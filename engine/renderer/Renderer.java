package engine.renderer;

import java.awt.*;

import engine.event.EventHandler;

public interface Renderer {
    public void painting(Graphics2D g2d);

    public EventHandler getEventHandler();
}
