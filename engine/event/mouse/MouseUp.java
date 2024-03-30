package engine.event.mouse;

import engine.event.Event;
import java.awt.geom.Point2D;

public class MouseUp extends Event<Point2D> {

    public MouseUp(Point2D p) {
        super("mouse.up",p);
    }
}