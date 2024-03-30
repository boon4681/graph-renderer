package engine.graph;

import java.awt.*;
import java.util.ArrayList;
import engine.renderer.PanZoom;
import engine.tick.Tickable;

public class GraphPanel extends PanZoom implements Tickable {
    private ArrayList<Vertex> vertexs = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public GraphPanel(int init_w, int init_h) {
        super(init_w, init_h);
    }

    public void addVertex(Vertex v) {
        this.vertexs.add(v);
    }

    public void addEdge(Edge e) {
        this.edges.add(e);
    }

    @Override
    public void painting(Graphics2D g2d) {
        for (Edge edge : edges) {
            edge.render( g2d);
        }
        for (Vertex vertex : vertexs) {
            vertex.render( g2d);
        }
    }
}
