package engine.object.graph;

import java.awt.*;

import engine.math.Vector2D;
import engine.object.Interactable;

public class Vertex implements Interactable {
    private double id = Math.random();
    public int x;
    public int y;
    public String name;
    private int radius = 20;
    private boolean hover = false;
    private boolean drag = false;

    public Vertex(int x, int y) {
        this("NaN", x, y);
    }

    public Vertex(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g) {
        int r = radius + 5;
        g = (Graphics2D) g.create();
        g.setColor(Color.ORANGE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        drawCenteredString(g, name, x, y);
        if (hover) {
            g.drawOval(x - r, y - r, r * 2, r * 2);
        }
        g.dispose();
    }

    public void drawCenteredString(Graphics2D g, String text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        x += -metrics.stringWidth(text) / 2;
        y += -metrics.getHeight() / 2 + metrics.getAscent();
        g.drawString(text, x, y);
    }

    @Override
    public double getId() {
        return this.id;
    }

    @Override
    public boolean testCollision(Vector2D mouse) {
        if (mouse.copy().sub(new Vector2D(this.x, this.y)).mag() < this.radius) {
            return true;
        }
        return false;
    }

    @Override
    public void onMouseEnter(Vector2D p) {
        this.hover = true;
    }

    @Override
    public void onMouseLeave(Vector2D p) {
        this.hover = false;
    }

    @Override
    public void onMouseDown(Vector2D p) {
        this.drag = true;
    }

    @Override
    public void onMouseUp(Vector2D p) {
        this.drag = false;
    }

    @Override
    public void onMouseMove(Vector2D p) {
        if (this.drag) {
            this.x = (int) p.x;
            this.y = (int) p.y;
        }
    }
}
