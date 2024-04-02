package engine.renderer;

import java.awt.Graphics2D;

import engine.math.Vector2D;

public interface Renderable {
    public void render(Panel parent,Graphics2D g);

    public double getId();

    public Vector2D getLocation();
}
