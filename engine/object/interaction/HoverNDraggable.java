package engine.object.interaction;

import engine.math.Vector2D;
import engine.renderer.Panel;

public abstract class HoverNDraggable implements Interactable {
    protected boolean hover = false;
    protected boolean drag = false;

    @Override
    public void onMouseDown(Panel parent, Vector2D p) {
        this.drag = true;
    }

    @Override
    public void onMouseUp(Panel parent, Vector2D p) {
        this.drag = false;
    }

    @Override
    public void onMouseEnter(Panel parent, Vector2D p) {
        this.hover = true;
    }

    @Override
    public void onMouseLeave(Panel parent, Vector2D p) {
        this.hover = false;
    }
}
