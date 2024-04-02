package engine.renderer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

import engine.event.mouse.MouseClicked;
import engine.event.mouse.MouseDown;
import engine.event.mouse.MouseMiddleDown;
import engine.event.mouse.MouseMove;
import engine.event.mouse.MouseRightDown;
import engine.event.mouse.MouseUp;
import engine.event.mouse.MouseWheel;
import engine.event.pan.Panning;

import java.awt.geom.AffineTransform;

public abstract class PanZoom extends Panel {
    private Point prevMouse = new Point();
    private Point offset = new Point();
    private AffineTransform transform = new AffineTransform();

    private boolean panning = false;

    public PanZoom(int init_w, int init_h, Initializer initializer) {
        super(init_w, init_h, true, initializer);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) {
                    Point point = new Point();
                    point.setLocation(
                            e.getPoint().getX() - offset.getX(),
                            e.getPoint().getY() - offset.getY());
                    handler.dispatchEvent(new MouseDown(point));
                    handler.dispatchEvent(new MouseClicked(point));
                }
                if (e.getButton() == 2) {
                    panning = true;
                    prevMouse.setLocation(e.getPoint());
                    Point point = new Point();
                    point.setLocation(
                            e.getPoint().getX() - offset.getX(),
                            e.getPoint().getY() - offset.getY());
                    handler.dispatchEvent(new MouseMiddleDown(point));
                }
                if (e.getButton() == 3) {
                    Point point = new Point();
                    point.setLocation(
                            e.getPoint().getX() - offset.getX(),
                            e.getPoint().getY() - offset.getY());
                    handler.dispatchEvent(new MouseRightDown(point));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                panning = false;
                prevMouse.setLocation(e.getPoint());
                Point point = new Point();
                point.setLocation(
                        e.getPoint().getX() - offset.getX(),
                        e.getPoint().getY() - offset.getY());
                handler.dispatchEvent(new MouseUp(point));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (panning) {
                    updatePanning(e.getPoint());
                }
                Point point = new Point();
                point.setLocation(
                        e.getPoint().getX() - offset.getX(),
                        e.getPoint().getY() - offset.getY());
                handler.dispatchEvent(new MouseMove(point));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (panning) {
                    updatePanning(e.getPoint());
                    handler.dispatchEvent(new Panning(e.getPoint()));
                }
                Point point = new Point();
                point.setLocation(
                        e.getPoint().getX() - offset.getX(),
                        e.getPoint().getY() - offset.getY());
                handler.dispatchEvent(new MouseMove(point));
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double d = (e.getPreciseWheelRotation() > 0 ? 0.9 : 1.1);
                zoom *= d;
                updateZooming(e, d);
                handler.dispatchEvent(new MouseWheel(e));
            }
        };
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
        // this.addMouseWheelListener(adapter);
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
        this.beforePainting((Graphics2D) g);
        this.getEventHandler().flush();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.transform(transform);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.painting(g2d);
        g2d.dispose();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.afterPainting(g2);
        g2.dispose();
    }

    protected abstract void beforePainting(Graphics2D g);

    protected abstract void afterPainting(Graphics2D g);
}
