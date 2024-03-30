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
        int r = radius + 5;
        g = (Graphics2D) g.create();
        g.setColor(Color.ORANGE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        drawCenteredString(g, name, x, y);
        g.drawOval(x - r, y - r, r * 2, r * 2);
        g.dispose();
    }

    public void drawCenteredString(Graphics2D g, String text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        x += -metrics.stringWidth(text) / 2;
        y += -metrics.getHeight() / 2 + metrics.getAscent();
        g.drawString(text, x, y);
    }
}
