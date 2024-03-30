package engine.event.mouse;

import engine.event.Event;
import java.awt.geom.Point2D;

public class MouseClicked extends Event<Point2D> {

    public MouseClicked(Point2D p) {
        super("mouseclick", p);
    }
}