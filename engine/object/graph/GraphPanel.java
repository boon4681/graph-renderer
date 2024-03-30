package engine.object.graph;

import java.awt.*;
import java.util.ArrayList;

import engine.event.EventListener;
import engine.math.Vector2D;
import engine.object.Interactable;
import engine.renderer.PanZoom;
import engine.tick.Tickable;

public class GraphPanel extends PanZoom implements Tickable {
    private ArrayList<Vertex> vertexs = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private Interactable prevEnter;
    private Interactable prevDown;

    public GraphPanel(int init_w, int init_h) {
        super(init_w, init_h);
        // mouse down event
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.down", (e) -> {
            Vector2D mouse = new Vector2D(e.getX(), e.getY());
            for (Vertex vertex : vertexs) {
                if (vertex.testCollision(mouse)) {
                    if (this.prevDown != null) {
                        if (this.prevDown.getId() != vertex.getId()) {
                            vertex.onMouseDown(mouse);
                            this.prevDown = vertex;
                        }
                    } else {
                        vertex.onMouseDown(mouse);
                        this.prevDown = vertex;
                    }
                    break;
                }
            }
            return true;
        }));
        // mouse up event
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.up", (e) -> {
            Vector2D mouse = new Vector2D(e.getX(), e.getY());
            this.prevDown.onMouseUp(mouse);
            return true;
        }));
        // mouse enter and leave event
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.move", (e) -> {
            Vector2D mouse = new Vector2D(e.getX(), e.getY());
            boolean enter = false;
            for (Vertex vertex : vertexs) {
                if (vertex.testCollision(mouse)) {
                    if (this.prevEnter != null) {
                        if (this.prevEnter.getId() != vertex.getId()) {
                            vertex.onMouseEnter(mouse);
                            this.prevEnter = vertex;
                        }
                    } else {
                        vertex.onMouseEnter(mouse);
                        this.prevEnter = vertex;
                    }
                    this.prevEnter.onMouseMove(mouse);
                    enter = true;
                    break;
                }
            }
            if (!enter) {
                if (this.prevEnter != null) {
                    this.prevEnter.onMouseLeave(mouse);
                    this.prevEnter = null;
                }
            }
            return true;
        }));
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
            edge.render(g2d);
        }
        for (Vertex vertex : vertexs) {
            vertex.render(g2d);
        }
    }
}
