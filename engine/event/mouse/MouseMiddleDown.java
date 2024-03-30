package engine.event.mouse;

import engine.event.Event;
import java.awt.geom.Point2D;

public class MouseMiddleDown extends Event<Point2D> {

    public MouseMiddleDown(Point2D p) {
        super("mouse.middle.down",p);
    }
}