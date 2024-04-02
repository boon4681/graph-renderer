package visualizer;

import java.awt.Component;
import javax.swing.JOptionPane;

import engine.object.graph.Arc;
import engine.object.graph.Arrow;
import engine.object.graph.GraphPanel;
import engine.object.graph.Vertex;
import engine.object.ui.Button;
import engine.renderer.Panel;
import visualizer.logic.automata.Finite;
import visualizer.logic.automata.Finite.AutomataResult;

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
        Button run = new Button("Run", 10, 55, 140, 30);
        run.onClicked = () -> {
            String text = JOptionPane.showInputDialog(parent, "Input some sequence of 0,1", null);
            Integer[] ints = text.chars().map((e) -> e - '0').boxed().toArray(Integer[]::new);
            for (int i : ints) {
                if (i != 0 && i != 1) {
                    JOptionPane.showMessageDialog(parent, "Failed, Found some not 0,1 characters");
                    return;
                }
            }
            AutomataResult output = Finite.integerInputAutomata(ints);
            if (output.result()) {
                JOptionPane.showMessageDialog(parent, "Accepted");
            } else {
                JOptionPane.showMessageDialog(parent, "Rejected");
            }
        };
        this.add(run);
        this.add(graph);
        if (initializer != null) {
            initializer.init(this);
        }
    }

}
