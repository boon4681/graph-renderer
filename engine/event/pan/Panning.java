package engine.event.pan;

import engine.event.Event;
import java.awt.geom.Point2D;

public class Panning extends Event<Point2D> {

    public Panning(Point2D p) {
        super("pan.panning", p);
    }
}