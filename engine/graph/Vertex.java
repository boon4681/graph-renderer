package engine.graph;

import java.awt.*;

import engine.renderer.Renderable;

public class Vertex implements Renderable {
    public int x;
    public int y;
    public String name;
    private static int radius = 20;

    public Vertex(int x, int y) {
        this("NaN", x, y);
    }

    public Vertex(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g) {
        g = (Graphics2D) g.create();
        g.setColor(Color.ORANGE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        drawCenteredString(g, name, x, y);
        // g.drawOval(80,80,150,150);
        g.dispose();
    }

    public void drawCenteredString(Graphics2D g, String text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        x += -metrics.stringWidth(text) / 2;
        y += -metrics.getHeight() / 2 + metrics.getAscent();
        g.drawString(text, x, y);
    }
}
