package engine.object.graph;

import java.awt.Graphics2D;

import engine.math.Vector2D;
import engine.object.Interactable;

public class Edge implements Interactable {
    private double id = Math.random();
    public Vertex start;
    public Vertex end;
    public int weight;

    public Edge(Vertex start, Vertex end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public void render(Graphics2D g) {
        g = (Graphics2D) g.create();
        g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);
        g.dispose();
    }

    @Override
    public double getId() {
        return this.id;
    }

    @Override
    public boolean testCollision(Vector2D mouse) {
        return false;
    }
}
