package engine.utils;

import java.awt.*;

public class Graphic {
    public static void drawCenteredString(Graphics2D g, String text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        x += -metrics.stringWidth(text) / 2;
        y += -metrics.getHeight() / 2 + metrics.getAscent();
        g.drawString(text, x, y);
    }
}
