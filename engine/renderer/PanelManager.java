package engine.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;

import javax.swing.JPanel;

import engine.event.EventHandler;
import engine.tick.Tickable;

public class PanelManager extends JPanel implements Renderer, Tickable {
    protected HashMap<String, Panel> maps = new HashMap<>();
    protected Panel rendering;

    public PanelManager(int init_w, int init_h) {
        this.setSize(init_w, init_h);
    }

    public void display(String name) {
        if (this.rendering != null) {
            this.remove(rendering);
        }
        this.rendering = this.maps.get(name);
        this.add(rendering);
    }

    @Override
    public void tick(double dt) {
        if (rendering != null) {
            if (rendering instanceof Tickable) {
                ((Tickable) rendering).tick(dt);
            }
            repaint();
        }
    }

    public void add(String name, Panel v) {
        this.maps.put(name, v);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.painting(g2d);
        g2d.dispose();
    }

    @Override
    public void painting(Graphics2D g2d) {
    }

    @Override
    public EventHandler getEventHandler() {
        return null;
    }
}
