package engine.graph;
import java.awt.Graphics2D;

import engine.renderer.Renderable;

public class Edge implements Renderable {
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
}
