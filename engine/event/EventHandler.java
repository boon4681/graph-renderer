package engine.event;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class EventHandler {
    private ArrayList<EventListener<?>> list = new ArrayList<>();
    private Queue<Event<?>> events = new ArrayDeque<>();

    public int addEventListener(EventListener<?> e) {
        return list.add(e) ? this.list.size() - 1 : -1;
    }

    public <T> void dispatchEvent(Event<T> e) {
        this.events.add(e);
    }

    @SuppressWarnings("unchecked")
    private <T> void _dispatchEvent(Event<T> e) {
        for (EventListener<?> listener : list) {
            if (e.isStopped()) {
                break;
            }
            if (listener.getName().equals(e.name)) {
                EventListener<T> listen = (EventListener<T>) listener;
                if (listen.on(e.details) == false) {
                    break;
                }
            }
        }
    }

    public void flush() {
        while (this.events.size() > 0) {
            this._dispatchEvent(this.events.poll());
        }
    }
}