package engine.object;

import engine.math.Vector2D;
import engine.renderer.Renderable;

public interface Interactable extends Renderable {
    public boolean testCollision(Vector2D p);

    public default void onClick(Vector2D p) {
    };

    public default void onMouseMove(Vector2D p) {
    };
    public default void onMouseDown(Vector2D p) {
    };

    public default void onMouseUp(Vector2D p) {
    };

    public default void onMouseEnter(Vector2D p) {
    };

    public default void onMouseLeave(Vector2D p) {
    };
}
