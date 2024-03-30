package engine.event.mouse;

import engine.event.Event;
import java.awt.event.MouseWheelEvent;

public class MouseWheel extends Event<MouseWheelEvent> {

    public MouseWheel(MouseWheelEvent p) {
        super("mousewheel", p);
    }
}