package engine.object.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import engine.event.EventListener;
import engine.math.Vector2D;
import engine.object.interaction.Interactable;
import engine.renderer.PanZoom;
import engine.utils.Image;

public class GraphPanel extends PanZoom {
    private BufferedImage mImage = Image.loadImage(new File("./assets/mouse.png"));
    public String tooltip = "";
    public MODE mode = MODE.DEFAULT;
    public Vector2D mouse = new Vector2D();
    public Edge prerender = new Edge(null, null, 0);

    public enum MODE {
        DEFAULT,
        EDGE,
        EDIT,
        DELETE
    }

    public GraphPanel(int init_w, int init_h) {
        this(init_w, init_h, null);
    }

    public GraphPanel(int init_w, int init_h, Initializer initializer) {
        super(init_w, init_h, initializer);
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.move", (e) -> {
            this.mouse = new Vector2D(e.getX(), e.getY());
            this.prerender.end = new Vertex((int) this.mouse.x, (int) this.mouse.y);
            return true;
        }));
        this.getEventHandler().addEventListener(new EventListener<Point>("mouse.right.down", (e) -> {
            this.prerender.start = null;
            return true;
        }));
        this.getEventHandler().addEventListener(new EventListener<Point>("pan.panning", (e) -> {
            this.prerender.start = null;
            return true;
        }));
    }

    public List<Vertex> getVertexs() {
        return Arrays.asList((this.objects.stream().filter((e) -> e instanceof Vertex).toArray(Vertex[]::new)));
    }

    public List<Edge> getEdges() {
        return Arrays.asList((this.objects.stream().filter((e) -> e instanceof Edge).toArray(Edge[]::new)));
    }

    @Override
    public void painting(Graphics2D g) {
        if (this.prerender.start != null && this.prerender.end != null && this.mode == MODE.EDGE) {
            if (this.prerender.start.getId() != this.prerender.end.getId()) {
                prerender.render(this, g);
            }
        }
        for (Interactable obj : objects) {
            if (!(obj instanceof Vertex) && !(obj instanceof Edge)) {
                obj.render(this, g);
            }
        }
        for (Interactable obj : objects) {
            if (obj instanceof Edge) {
                obj.render(this, g);
            }
        }
        for (Interactable obj : objects) {
            if (obj instanceof Vertex) {
                obj.render(this, g);
            }
        }
    }

    public void createEdge(Vertex node) {
        if (this.prerender.start != null) {
            if (this.prerender.start.getId() != node.getId()) {
                this.prerender.end = node;
                for (Interactable obj : objects) {
                    if (obj instanceof Edge) {
                        Edge edge = (Edge) obj;
                        if ((edge.start.getId() == this.prerender.start.getId()
                                && edge.end.getId() == this.prerender.end.getId())
                                || (edge.start.getId() == this.prerender.end.getId()
                                        && edge.end.getId() == this.prerender.start.getId())) {
                            this.prerender = new Edge(null, null, 0);
                            return;
                        }
                    }
                }
                objects.add(this.prerender);
                this.prerender = new Edge(null, null, 0);
            } else {
                this.prerender.start = null;
            }
        } else {
            this.prerender.start = node;
        }
    }

    public void deleteObject(Interactable e) {
        this.prerender.start = null;
        this.prerender.end = null;
        if (e instanceof Vertex) {
            Stack<Interactable> stack = new Stack<>();
            stack.addAll(objects);
            while (!stack.empty()) {
                Interactable obj = stack.pop();
                if (obj instanceof Edge) {
                    if (((Edge) obj).start.getId() == e.getId() || ((Edge) obj).end.getId() == e.getId()) {
                        this.objects.remove(obj);
                    }
                }
            }
        }
        this.objects.remove(e);
    }

    public Edge findEdge(Vertex v1, Vertex v2) {
        for (Edge edge : this.getEdges()) {
            if (edge.start.getId() == v1.getId() && edge.end.getId() == v2.getId()
                    || edge.start.getId() == v2.getId() && edge.end.getId() == v1.getId()) {
                return edge;
            }
        }
        return null;
    }

    @Override
    protected void beforePainting(Graphics2D g) {
    }

    @Override
    protected void afterPainting(Graphics2D g) {
        g.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 10, 10);
        g.drawImage(mImage, 20, this.getHeight() - 45, 200, 30, null);
        g = (Graphics2D) g.create();
        g.setColor(Color.GRAY);
        int i = 0;
        for (String line : this.tooltip.split("\n")) {
            g.drawString(line, 20, 280 + 20 * (i++));
        }
        g.dispose();
    }
}
