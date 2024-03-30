package engine.renderer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.geom.Point2D;

import engine.event.EventHandler;
import engine.event.EventListener;
import engine.event.mouse.MouseDown;
import engine.event.mouse.MouseMove;
import engine.event.mouse.MouseUp;
import engine.event.mouse.MouseWheel;
import java.awt.geom.AffineTransform;

public abstract class PanZoom extends JPanel implements Renderer {
    private double zoom = 1;
    private Point prevMouse = new Point();
    private Point offset = new Point();
    private AffineTransform transform = new AffineTransform();

    private boolean panning = false;
    private EventHandler handler = new EventHandler();
    // thanks https://stackoverflow.com/a/75457960
    private final double windowScale = java.awt.GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration()
            .getDefaultTransform()
            .getScaleX();

    public PanZoom(int init_w, int init_h) {
        this.setSize(init_w, init_h);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handler.dispatchEvent(new MouseDown(e.getPoint()));
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
            public void mouseDragged(MouseEvent e) {
                handler.dispatchEvent(new MouseMove(e.getPoint()));
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handler.dispatchEvent(new MouseWheel(e));
            }
        };
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
        this.addMouseWheelListener(adapter);

        handler.addEventListener(new EventListener<Point2D>("mousedown", (e) -> {
            this.panning = true;
            this.prevMouse.setLocation(e);
            return true;
        }));
        handler.addEventListener(new EventListener<Point2D>("mouseup", (e) -> {
            this.panning = false;
            this.prevMouse.setLocation(e);
            return true;
        }));
        handler.addEventListener(new EventListener<Point2D>("mousemove", (e) -> {
            if (this.panning) {
                updatePanning(e);
            }
            return true;
        }));
        handler.addEventListener(new EventListener<MouseWheelEvent>("mousewheel", (e) -> {
            double d = (e.getPreciseWheelRotation() > 0 ? 0.9 : 1.1);
            zoom *= d;
            updateZooming(e, d);
            return true;
        }));
    }

    private void updatePanning(Point2D end) {
        this.transform = new AffineTransform();
        this.offset.setLocation(
                this.offset.getX() + end.getX() - prevMouse.getX(),
                this.offset.getY() + end.getY() - prevMouse.getY());
        this.transform.translate(this.offset.getX(), this.offset.getY());
        this.transform.scale(zoom, zoom);
        this.prevMouse.setLocation(end);
    }

    private void updateZooming(MouseWheelEvent e, double d) {
        this.transform = new AffineTransform();
        Point end = e.getPoint();
        this.offset.setLocation(
                this.offset.getX() * d + (1 - d) * end.getX(),
                this.offset.getY() * d + (1 - d) * end.getY());
        this.transform.translate(this.offset.getX(), this.offset.getY());
        this.transform.scale(zoom, zoom);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.getEventHandler().flush();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.transform(transform);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.painting(g2d);
        g2d.dispose();
    }

    public void tick(double dt) {
        repaint();
    }

    @Override
    public EventHandler getEventHandler() {
        return this.handler;
    }
}
