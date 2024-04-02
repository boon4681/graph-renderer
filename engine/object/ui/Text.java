package engine.object.ui;

import java.awt.Graphics2D;

import engine.math.Vector2D;
import engine.object.interaction.Interactable;
import engine.renderer.Panel;
import engine.utils.Graphic;

public class Text implements Interactable {
    private double id = Math.random();
    public boolean center;
    public String text;
    public int x;
    public int y;
    public float fontSize;

    public Text(String text) {
        this(text, 0, 0);
    }

    public Text(String text, int x, int y) {
        this(text, x, y, 12, false);
    }

    public Text(String text, int x, int y, float fontSize) {
        this(text, x, y, fontSize, false);
    }

    public Text(String text, int x, int y, float fontSize, boolean center) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.center = center;
    }

    @Override
    public void render(Panel parent, Graphics2D g) {
        g = (Graphics2D) g.create();
        g.setFont(g.getFont().deriveFont(fontSize));
        if (this.center) {
            Graphic.drawCenteredString(g, this.text, this.x, this.y);
        } else {
            g.drawString(text, this.x, this.y);
        }
        g.dispose();
    }

    @Override
    public double getId() {
        return this.id;
    }

    @Override
    public Vector2D getLocation() {
        return null;
    }

    @Override
    public boolean testCollision(double zoom, Vector2D p) {
        return true;
    }

}
