package engine.object.interaction;

import engine.math.Vector2D;
import engine.renderer.Panel;

public abstract class Draggable implements Interactable {
    protected boolean drag = false;

    @Override
    public void onMouseDown(Panel parent, Vector2D p) {
        this.drag = true;
    }

    @Override
    public void onMouseUp(Panel parent, Vector2D p) {
        this.drag = false;
    }
}
