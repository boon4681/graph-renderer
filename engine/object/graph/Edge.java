package engine.object.graph;

import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import java.awt.BasicStroke;
import java.awt.Color;

import engine.math.Vector2D;
import engine.object.graph.GraphPanel.MODE;
import engine.object.interaction.Hoverable;
import engine.renderer.Panel;
import engine.utils.Graphic;

public class Edge extends Hoverable {
    private double id = Math.random();
    public Vertex start;
    public Vertex end;
    public double weight;

    public Edge(Vertex start, Vertex end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public void render(Panel parent, Graphics2D g) {
        g = (Graphics2D) g.create();
        if (this.hover) {
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(2));
        }
        Vector2D v = start.getLocation().copy().add(end.getLocation()).mult(0.5);
        v.y -= 10;
        Graphic.drawCenteredString(g, "" + weight, (int) v.x, (int) v.y);
        g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);
        g.dispose();
    }

    @Override
    public double getId() {
        return this.id;
    }

    @Override
    public boolean testCollision(double zoom, Vector2D mouse) {
        Vector2D start = this.start.getLocation();
        Vector2D end = this.end.getLocation();
        int len = (int) (start.copy().sub(end).mag() / 6);
        for (int i = 0; i < len; i++) {
            if (start.linearInterpolate(end, len, i).sub(mouse).mag() < 6) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Vector2D getLocation() {
        return null;
    }

    @Override
    public void onClick(Panel parent, Vector2D p) {
        if (((GraphPanel) parent).mode == MODE.EDIT) {
            String v = JOptionPane.showInputDialog(
                    parent,
                    "Set weight of edge",
                    null);
            if (v != null) {
                try {
                    double r = Double.parseDouble(v);
                    this.weight = r;
                } catch (Exception e) {
                }
            }
        }
        if (((GraphPanel) parent).mode == MODE.DELETE) {
            ((GraphPanel) parent).deleteObject(this);
        }
    }
}
