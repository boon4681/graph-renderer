import javax.swing.JFrame;
import engine.object.ui.Button;
import engine.object.ui.StaticImage;
import engine.object.ui.Text;
import engine.renderer.Panel;
import engine.renderer.PanelManager;
import engine.tick.Ticker;
import visualizer.AutomataVisualizer;
import visualizer.DijlstraVisualizer;
import visualizer.KruskalVisualizer;
import visualizer.PrimsVisualizer;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Graph visualizer");
        window.setSize(1000, 600);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setResizable(false);
        Ticker ticker = new Ticker();

        PanelManager manager = new PanelManager(1000, 600);

        Panel home = new Panel(1000, 600, (panel) -> {
            StaticImage bg = new StaticImage("assets/home.png", 10, 10, 600, 200);
            Button prims = new Button("Prim's algorithm", 10, 210, 200, 30);
            Button kruskal = new Button("Kruskal's algorithm", 10, 250, 200, 30);
            Button dijkstra = new Button("Dijkstra algorithm", 10, 290, 200, 30);
            Button automata = new Button("Finite automata", 10, 330, 200, 30);
            prims.onClicked = () -> {
                manager.display("prims");
            };
            kruskal.onClicked = () -> {
                manager.display("kruskal");
            };
            dijkstra.onClicked = () -> {
                manager.display("dijkstra");
            };
            automata.onClicked = () -> {
                manager.display("automata");
            };
            panel.add(prims);
            panel.add(kruskal);
            panel.add(dijkstra);
            panel.add(automata);
            panel.add(bg);
        });

        Panel prims = new PrimsVisualizer(window, 1000, 600, (panel) -> {
            Button back = new Button("<<", 10, 10, 60, 30);
            back.onClicked = () -> manager.display("home");
            panel.add(back);
            panel.add(new Text("Prim's algorithm", 160, 77, 20));
        });

        Panel kruskal = new KruskalVisualizer(window, 1000, 600, (panel) -> {
            Button back = new Button("<<", 10, 10, 60, 30);
            back.onClicked = () -> manager.display("home");
            panel.add(back);
            panel.add(new Text("Kruskal's algorithm", 160, 77, 20));
        });
        Panel dijkstra = new DijlstraVisualizer(window, 1000, 600, (panel) -> {
            Button back = new Button("<<", 10, 10, 60, 30);
            back.onClicked = () -> manager.display("home");
            panel.add(back);
            panel.add(new Text("Dijkstra algorithm", 160, 77, 20));
        });
        Panel automata = new AutomataVisualizer(window, 1000, 600, (panel) -> {
            Button back = new Button("<<", 10, 10, 60, 30);
            back.onClicked = () -> manager.display("home");
            panel.add(back);
            panel.add(new Text("Finite automata", 160, 77, 20));
        });

        manager.add("home", home);
        manager.add("prims", prims);
        manager.add("kruskal", kruskal);
        manager.add("dijkstra", dijkstra);
        manager.add("automata", automata);
        manager.display("home");
        ticker.add(manager);
        window.add(manager);
    }
}
