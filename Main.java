import javax.swing.JFrame;

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
        gPanel.addVertex(new Vertex(0, 0));
        ticker.add(gPanel);
        window.add(gPanel);
    }
}
