package visualizer;

import java.awt.Component;

import javax.swing.JOptionPane;
import engine.object.graph.GraphPanel;
import engine.object.graph.Vertex;
import engine.object.graph.GraphPanel.MODE;
import engine.object.ui.Button;
import engine.object.ui.ImageButton;
import engine.renderer.Panel;

public class GraphEditor extends Panel {
    protected GraphPanel graph = new GraphPanel(965, 400);

    public GraphEditor(Component parent, int init_w, int init_h, Initializer initializer) {
        super(init_w, init_h, null);
        ImageButton defaultBtn = new ImageButton("./assets/default.png", 10, 55, 30, 30);
        ImageButton addEdge = new ImageButton("./assets/edge.png", 45, 55, 30, 30);
        ImageButton editBtn = new ImageButton("./assets/edit.png", 80, 55, 30, 30);
        ImageButton deleteBtn = new ImageButton("./assets/delete.png", 115, 55, 30, 30);
        // Button add_node = new Button("Add new node", 10, 450);
        Button addNode = new Button("Add new node", 860, 55, 115, 30);
        graph.setLocation(10, 42 + 50);
        graph.tooltip = "tooltip:\n - Normal mouse";
        defaultBtn.onEntered = () -> graph.tooltip = "tooltip:\n - Normal mouse";
        addEdge.onEntered = () -> graph.tooltip = "tooltip:\n - Click on node to connect\n them\n - Right-click to cancel";
        editBtn.onEntered = () -> graph.tooltip = "tooltip:\n - Left-click on edge to edit\n their weight";
        deleteBtn.onEntered = () -> graph.tooltip = "tooltip:\n - Click on node or edge to delete\n them";
        defaultBtn.onClicked = () -> {
            defaultBtn.focus = true;
            addEdge.focus = false;
            editBtn.focus = false;
            deleteBtn.focus = false;
            graph.mode = MODE.DEFAULT;
            graph.tooltip = "tooltip:\n - Normal mouse";
        };
        addEdge.onClicked = () -> {
            if (addEdge.focus) {
                addEdge.focus = false;
                defaultBtn.focus = true;
                graph.mode = MODE.DEFAULT;
            } else {
                defaultBtn.focus = false;
                addEdge.focus = true;
                editBtn.focus = false;
                deleteBtn.focus = false;
                graph.mode = MODE.EDGE;
                graph.tooltip = "tooltip:\n - Click on node to connect\n them\n - Right-click to cancel";
            }
        };
        editBtn.onClicked = () -> {
            if (editBtn.focus) {
                editBtn.focus = false;
                defaultBtn.focus = true;
                graph.mode = MODE.DEFAULT;
            } else {
                defaultBtn.focus = false;
                addEdge.focus = false;
                editBtn.focus = true;
                deleteBtn.focus = false;
                graph.mode = MODE.EDIT;
                graph.tooltip = "tooltip:\n - Left-click on edge to edit\n their weight";
            }
        };
        deleteBtn.onClicked = () -> {
            if (deleteBtn.focus) {
                deleteBtn.focus = false;
                defaultBtn.focus = true;
                graph.mode = MODE.DEFAULT;
            } else {
                defaultBtn.focus = false;
                addEdge.focus = false;
                editBtn.focus = false;
                deleteBtn.focus = true;
                graph.mode = MODE.DELETE;
                graph.tooltip = "tooltip:\n - Click on node or edge to delete\n them";
            }
        };
        addNode.onClicked = () -> {
            String name = JOptionPane.showInputDialog(
                    parent,
                    "Named the node.",
                    null);
            if (name != null) {
                if (graph.getVertexs().stream().filter(e -> e.name.equals(name)).count() == 1) {
                    JOptionPane.showMessageDialog(parent, "Failed, Node with \"" + name + "\" is already exist.");
                } else {
                    graph.add(
                            new Vertex(name, (int) (Math.random() * 500) + 250, (int) (Math.random() * 200) + 100));
                }
            }
        };
        defaultBtn.focus = true;
        this.add(defaultBtn);
        this.add(addEdge);
        this.add(editBtn);
        this.add(deleteBtn);
        this.add(addNode);
        this.add(graph);
        if (initializer != null) {
            initializer.init(this);
        }
    }
}
