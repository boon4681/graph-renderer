package visualizer;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JOptionPane;
import engine.object.ui.Button;
import visualizer.logic.prims.Prims;

public class PrimsVisualizer extends GraphEditor {

    public PrimsVisualizer(Component parent, int init_w, int init_h, Initializer initializer) {
        super(parent, init_w, init_h, null);
        Button run = new Button("Run", 710, 55, 140, 30);
        run.onClicked = () -> {
            String name = JOptionPane.showInputDialog(
                    parent,
                    "Put the name of starting node.",
                    null);
            if (name != null) {
                if (graph.getVertexs().stream().filter(e -> e.name.equals(name)).count() > 0) {
                    Prims prims = new Prims(graph.getVertexs().size());
                    // Prims graph = new Prims(9);
                    // graph.addEdge(0, 1, 4);
                    // graph.addEdge(0, 7, 8);
                    // graph.addEdge(1, 7, 11);
                    // graph.addEdge(1, 2, 8);
                    // graph.addEdge(7, 8, 7);
                    // graph.addEdge(7, 6, 1);
                    // graph.addEdge(2, 8, 2);
                    // graph.addEdge(8, 2, 2);
                    // graph.addEdge(6, 8, 6);
                    // graph.addEdge(8, 6, 6);
                    // graph.addEdge(2, 5, 4);
                    // graph.addEdge(5, 2, 4);
                    // graph.addEdge(6, 5, 2);
                    // graph.addEdge(2, 3, 7);
                    // graph.addEdge(3, 5, 14);
                    // graph.addEdge(5, 3, 14);
                    // graph.addEdge(3, 4, 9);
                    // graph.addEdge(5, 4, 10);

                    // graph.run(0);
                } else {
                    JOptionPane.showMessageDialog(parent, "Failed, Node with \"" + name + "\" is doesn't exist.");
                }
            }
        };
        this.add(run);
        if (initializer != null) {
            initializer.init(this);
        }
    }
}
