package engine.event.mouse;

import engine.event.Event;
import java.awt.geom.Point2D;

public class MouseMove extends Event<Point2D> {

    public MouseMove(Point2D p) {
        super("mousemove", p);
    }
}