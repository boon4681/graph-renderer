package visualizer;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JOptionPane;

import engine.object.graph.Edge;
import engine.object.graph.Vertex;
import engine.object.ui.Button;
import visualizer.logic.prims.Prims;
import visualizer.logic.prims.Prims.PrimsRecord;

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
                    Prims prims = new Prims();
                    HashMap<String, Integer> map = new HashMap<>();
                    HashMap<Integer, Vertex> remap = new HashMap<>();
                    for (Edge edge : graph.getEdges()) {
                        edge.highlight = false;
                    }
                    for (Vertex v : graph.getVertexs()) {
                        map.put(v.name, map.size());
                        remap.put(map.size() - 1, v);
                    }
                    for (Edge edge : graph.getEdges()) {
                        prims.addEdge(map.get(edge.start.name), map.get(edge.end.name), (int) edge.weight);
                    }
                    for (PrimsRecord record : prims.run(map.get(name))) {
                        Edge edge = graph.findEdge(remap.get(record.from()), remap.get(record.to()));
                        if (edge != null) {
                            edge.highlight = true;
                        }
                    }
                    System.out.println(map);
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
