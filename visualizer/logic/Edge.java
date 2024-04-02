package visualizer.logic;

public class Edge {
    public int start;
    public int end;
    public int weight;
    public int v;

    public Edge(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Edge(int v, int weight) {
        this.v = v;
        this.weight = weight;
    }
}