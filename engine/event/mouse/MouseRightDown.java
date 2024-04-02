package engine.event.mouse;

import engine.event.Event;
import java.awt.geom.Point2D;

public class MouseRightDown extends Event<Point2D> {

    public MouseRightDown(Point2D p) {
        super("mouse.right.down", p);
    }
}