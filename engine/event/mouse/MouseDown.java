package engine.event.mouse;

import engine.event.Event;
import java.awt.geom.Point2D;

public class MouseDown extends Event<Point2D> {

    public MouseDown(Point2D p) {
        super("mouse.down",p);
    }
}