package engine.object.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import engine.math.Vector2D;
import engine.renderer.Panel;

public class Arrow extends Edge {
    private double angle = 0.3;

    public Arrow(Vertex start, Vertex end, int weight) {
        super(start, end, weight);
    }

    public void render(Panel parent, Graphics2D g) {
        super.render(parent, g);
        g = (Graphics2D) g.create();
        Vector2D s = this.start.getLocation();
        Vector2D e = this.end.getLocation();
        e.sub(e.copy().sub(s).normalize().mult(20));
        Vector2D v = e.copy().sub(s);
        int k = this.start.x > this.end.x ? 20 : -20;
        double d = v.atan();
        int x = (int) e.x;
        int y = (int) e.y;
        if (this.hover) {
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(2));
        }
        g.drawLine(x, y, x + (int) (k * Math.cos(d + angle)), y + (int) (k * Math.sin(d + angle)));
        g.drawLine(x, y, x + (int) (k * Math.cos(d - angle)), y + (int) (k * Math.sin(d - angle)));
        g.dispose();
    }

}
