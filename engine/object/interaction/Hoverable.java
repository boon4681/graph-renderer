package engine.object.interaction;

import engine.math.Vector2D;
import engine.renderer.Panel;

public abstract class Hoverable implements Interactable {
    protected boolean hover = false;

    @Override
    public void onMouseEnter(Panel parent, Vector2D p) {
        this.hover = true;
    }

    @Override
    public void onMouseLeave(Panel parent, Vector2D p) {
        this.hover = false;
    }
}
