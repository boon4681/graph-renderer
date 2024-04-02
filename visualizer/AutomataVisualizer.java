package visualizer;

import java.awt.Component;

import engine.object.graph.Arc;
import engine.object.graph.Arrow;
import engine.object.graph.GraphPanel;
import engine.object.graph.Vertex;
import engine.renderer.Panel;

public class AutomataVisualizer extends Panel {

    public AutomataVisualizer(Component parent, int init_w, int init_h, Initializer initializer) {
        super(init_w, init_h, null);
        GraphPanel graph = new GraphPanel(965, 400);
        graph.setLocation(10, 42 + 50);
        Vertex s0 = new Vertex("s0", 250, 150);
        Vertex s1 = new Vertex("s1", 450, 250);
        Vertex s2 = new Vertex("s2", 650, 150);
        graph.add(new Arrow(s0, s1, 0));
        graph.add(new Arc(s0, 1));
        graph.add(new Arc(s1, 0));
        graph.add(new Arrow(s1, s2, 1));
        graph.add(new Arrow(s2, s1, 0));
        graph.add(new Arrow(s2, s0, 1));

        graph.add(s0);
        graph.add(s1);
        graph.add(s2);
        this.add(graph);
        if (initializer != null) {
            initializer.init(this);
        }
    }

}
