package engine.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import java.util.ArrayList;

import engine.event.EventHandler;
import engine.event.EventListener;
import engine.event.mouse.MouseClicked;
import engine.event.mouse.MouseDown;
import engine.event.mouse.MouseMiddleDown;
import engine.event.mouse.MouseMove;
import engine.event.mouse.MouseRightDown;
import engine.event.mouse.MouseUp;
import engine.event.mouse.MouseWheel;
import engine.math.Vector2D;
import engine.object.interaction.Interactable;
import engine.tick.Tickable;

public class Panel extends JPanel implements Renderer, Tickable {
    protected EventHandler handler = new EventHandler();
    protected ArrayList<Interactable> objects = new ArrayList<>();
    protected Interactable prevEnter;
    protected Interactable prevDown;
    protected double zoom = 1;

    public interface Initializer {
        public void init(Panel panel);
    }

    public Panel(int init_w, int init_h, Initializer initializer) {
        this(init_w, init_h, false, initializer);
    }

    public Panel(int init_w, int init_h, boolean overwrite, Initializer initializer) {
        this.setSize(init_w, init_h);
        if (!overwrite) {
            MouseAdapter adapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == 1) {
                        handler.dispatchEvent(new MouseDown(e.getPoint()));
                        handler.dispatchEvent(new MouseClicked(e.getPoint()));
                    }
                    if (e.getButton() == 2) {
                        handler.dispatchEvent(new MouseMiddleDown(e.getPoint()));
                    }
                    if (e.getButton() == 3) {
                        handler.dispatchEvent(new MouseRightDown(e.getPoint()));
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    handler.dispatchEvent(new MouseUp(e.getPoint()));
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    handler.dispatchEvent(new MouseMove(e.getPoint()));
                }

                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    handler.dispatchEvent(new MouseWheel(e));
                }
            };
            this.addMouseListener(adapter);
            this.addMouseMotionListener(adapter);
        }
        // mouse down event
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.down", (e) -> {
            Vector2D mouse = new Vector2D(e.getX() * this.zoom, e.getY() * this.zoom);
            for (Interactable obj : objects) {
                if (obj.testCollision(this.zoom, mouse)) {
                    if (this.prevDown != null) {
                        if (this.prevDown.getId() != obj.getId()) {
                            this.prevDown.onMouseUp(this, mouse);
                            obj.onMouseDown(this, mouse);
                            this.prevDown = obj;
                        }
                    } else {
                        obj.onMouseDown(this, mouse);
                        this.prevDown = obj;
                    }
                    return false;
                }
            }
            return true;
        }));
        // mouse clicked event
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.click", (e) -> {
            Vector2D mouse = new Vector2D(e.getX() * this.zoom, e.getY() * this.zoom);
            for (Interactable obj : objects) {
                if (obj.testCollision(this.zoom, mouse)) {
                    if (this.prevDown != null) {
                        obj.onClick(this, mouse);
                    } else {
                        obj.onClick(this, mouse);
                    }
                    return false;
                }
            }
            return true;
        }));
        // mouse up event
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.up", (e) -> {
            Vector2D mouse = new Vector2D(e.getX() * this.zoom, e.getY() * this.zoom);
            if (this.prevDown != null) {
                this.prevDown.onMouseUp(this, mouse);
                this.prevDown = null;
                return false;
            }
            return true;
        }));
        // mouse enter and leave event
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.move", (e) -> {
            Vector2D mouse = new Vector2D(e.getX() * this.zoom, e.getY() * this.zoom);
            for (Interactable obj : objects) {
                if (obj.testCollision(this.zoom, mouse)) {
                    if (this.prevEnter != null) {
                        if (this.prevEnter.getId() != obj.getId() && this.prevDown == null) {
                            this.prevEnter.onMouseLeave(this, mouse);
                            obj.onMouseEnter(this, mouse);
                            this.prevEnter = obj;
                        }
                    } else {
                        obj.onMouseEnter(this, mouse);
                        this.prevEnter = obj;
                    }
                    this.prevEnter.onMouseMove(this, mouse);
                    return false;
                }
            }
            if (this.prevEnter != null) {
                this.prevEnter.onMouseLeave(this, mouse);
                this.prevEnter = null;
                return false;
            }
            return true;
        }));
        if (initializer != null) {
            initializer.init(this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.getEventHandler().flush();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.painting(g2d);
        g2d.dispose();
    }

    public void tick(double dt) {
        for (Interactable obj : objects) {
            if (obj instanceof Tickable) {
                ((Tickable) obj).tick(dt);
            }
        }
        repaint();
    }

    @Override
    public EventHandler getEventHandler() {
        return this.handler;
    }

    public void add(Interactable v) {
        this.objects.add(v);
    }

    @Override
    public void painting(Graphics2D g2d) {
        for (Interactable obj : objects) {
            obj.render(this, g2d);
        }
    }
}
