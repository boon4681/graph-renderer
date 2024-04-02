package engine.object.graph;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.math.Vector2D;
import engine.object.graph.GraphPanel.MODE;
import engine.object.interaction.HoverNDraggable;
import engine.renderer.Panel;
import engine.utils.Graphic;

public class Vertex extends HoverNDraggable {
    private double id = Math.random();
    public int x;
    public int y;
    public String name;
    private int radius = 20;

    public Vertex(int x, int y) {
        this("NaN", x, y);
    }

    public Vertex(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void render(Panel parent, Graphics2D g) {
        int r = radius + 5;
        g = (Graphics2D) g.create();
        g.setColor(Color.ORANGE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        Graphic.drawCenteredString(g, name, x, y);
        if (hover) {
            if (((GraphPanel) parent).mode == MODE.DELETE) {
                g.setColor(Color.RED);
            }
            g.drawOval(x - r, y - r, r * 2, r * 2);
        }
        g.dispose();
    }

    @Override
    public double getId() {
        return this.id;
    }

    @Override
    public boolean testCollision(double zoom, Vector2D mouse) {
        if (mouse.copy().sub(this.getLocation()).mag() < this.radius * zoom) {
            return true;
        }
        return false;
    }

    @Override
    public void onMouseMove(Panel parent, Vector2D p) {
        if (this.drag) {
            this.x = (int) p.x;
            this.y = (int) p.y;
            ((GraphPanel) parent).prerender.start = null;
        }
    }

    @Override
    public void onClick(Panel parent, Vector2D p) {
        if (((GraphPanel) parent).mode == MODE.EDGE) {
            ((GraphPanel) parent).createEdge(this);
        }
        if (((GraphPanel) parent).mode == MODE.DELETE) {
            ((GraphPanel) parent).deleteObject(this);
        }
    }

    @Override
    public Vector2D getLocation() {
        return new Vector2D(this.x, this.y);
    }
}
