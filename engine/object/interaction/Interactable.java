package engine.object.interaction;

import engine.math.Vector2D;
import engine.renderer.Panel;
import engine.renderer.Renderable;

public interface Interactable extends Renderable {
    public boolean testCollision(double zoom, Vector2D p);

    public default void onClick(Panel parent, Vector2D p) {
    };

    public default void onMouseMove(Panel parent, Vector2D p) {
    };

    public default void onMouseDown(Panel parent, Vector2D p) {
    };

    public default void onMouseUp(Panel parent, Vector2D p) {
    };

    public default void onMouseEnter(Panel parent, Vector2D p) {
    };

    public default void onMouseLeave(Panel parent, Vector2D p) {
    };
}
