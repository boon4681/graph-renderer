package engine.renderer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.geom.Point2D;

import engine.event.EventHandler;
import engine.event.EventListener;
import engine.event.mouse.MouseClicked;
import engine.event.mouse.MouseDown;
import engine.event.mouse.MouseMove;
import engine.event.mouse.MouseUp;
import engine.event.mouse.MouseWheel;
import engine.graph.Edge;
import engine.graph.Vertex;
import engine.math.Vector2D;

import java.util.ArrayList;
import java.awt.geom.AffineTransform;

public abstract class PanZoom extends JPanel implements Renderer {
    private double zoom = 1;
    private final Point2D start = new Point();
    private boolean panning = false;
    private final AffineTransform coord = new AffineTransform();
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
            start.setLocation(e);
            return true;
        }));
        handler.addEventListener(new EventListener<Point2D>("mouseup", (e) -> {
            this.panning = false;
            start.setLocation(e);
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
        Point2D dragStart = transformPoint(start);
        Point2D dragEnd = transformPoint(end);
        double tx = (dragEnd.getX() - dragStart.getX()) * windowScale;
        double ty = (dragEnd.getY() - dragStart.getY()) * windowScale;
        coord.translate(tx, ty);
        start.setLocation(end);
    }

    private void updateZooming(MouseWheelEvent e, double d) {
        Point2D end = transformPoint(e.getPoint());

        coord.translate(end.getX(), end.getY());
        coord.scale(d, d);
        coord.translate(-end.getX(), -end.getY());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.getEventHandler().flush();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setTransform(coord);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // RenderingHints rh = new RenderingHints(
        // RenderingHints.KEY_TEXT_ANTIALIASING,
        // RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // g2d.setRenderingHints(rh);
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

    private Point2D transformPoint(Point2D p1) {
        AffineTransform inverse = coord;
        boolean hasInverse = coord.getDeterminant() != 0d;
        if (hasInverse) {
            try {
                inverse = coord.createInverse();
            } catch (Exception ex) {
                assert false;
            }
        }
        Point2D p2 = new Point();
        inverse.transform(p1, p2);
        return p2;
    }
}
