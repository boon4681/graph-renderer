import javax.swing.JFrame;

import engine.graph.Edge;
import engine.graph.GraphPanel;
import engine.graph.Vertex;
import engine.tick.Ticker;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(1000, 600);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setResizable(false);
        Ticker ticker = new Ticker();
        GraphPanel gPanel = new GraphPanel(window.getWidth(), window.getHeight());
        Vertex v1 = new Vertex(50, 40);
        Vertex v2 = new Vertex(160, 60);

        gPanel.addVertex(v1);
        gPanel.addVertex(v2);
        gPanel.addEdge(new Edge(v1, v2, 0));
        
        ticker.add(gPanel);
        window.add(gPanel);
    }
}
