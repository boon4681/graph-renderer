package engine.object.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import engine.math.Vector2D;
import engine.object.interaction.Hoverable;
import engine.renderer.Panel;
import engine.utils.Graphic;

public class Arc extends Hoverable {
    private double id = Math.random();
    private int r = 20;
    public Vertex main;
    public double weight;

    public Arc(Vertex main, int weight) {
        this.main = main;
        this.weight = weight;
    }

    public void render(Panel parent, Graphics2D g) {
        g = (Graphics2D) g.create();
        Vector2D s = this.main.getLocation();
        int x = (int) s.x;
        int y = (int) s.y;
        if (this.hover) {
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(2));
        }
        Graphic.drawCenteredString(g, "" + weight, x - r - 5, y - r * 2 - 10);
        g.drawOval(x - r * 2, y - r * 2, r * 2, r * 2);
        g.dispose();
    }

    @Override
    public boolean testCollision(double zoom, Vector2D p) {
        return false;
    }

    @Override
    public double getId() {
        return this.id;
    }

    @Override
    public Vector2D getLocation() {
        return null;
    }
}