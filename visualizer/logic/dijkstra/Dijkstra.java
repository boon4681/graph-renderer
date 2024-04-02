package visualizer.logic.dijkstra;

import java.util.*;

import visualizer.logic.Edge;

public class Dijkstra {
    private int V;
    private List<List<Edge>> adj;

    public static record DijkstraRecord(int from, int to) {
        @Override
        public final String toString() {
            return from + " -> " + to;
        }
    }

    public Dijkstra(int v) {
        V = v;
        adj = new ArrayList<>(v);
        for (int i = 0; i < v; ++i)
            adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Edge(v, weight));
        adj.get(v).add(new Edge(u, weight));
    }

    public Stack<DijkstraRecord> run(int start) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        int[] parent = new int[V]; // To store the parent vertex for each vertex in the shortest path
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1); // Initialize parent array with -1
        dist[start] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (Edge edge : adj.get(u)) {
                int v = edge.v;
                int weight = edge.weight;
                if (!visited[v] && dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u; // Update parent vertex
                }
            }
        }

        Stack<DijkstraRecord> records = new Stack<>();
        for (int i = 0; i < V; i++) {
            if (i != start && dist[i] != Integer.MAX_VALUE) { // Exclude starting vertex and unreachable nodes
                records.push(new DijkstraRecord(parent[i], i));
            }
        }
        return records;
    }

    private int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int v = 0; v < V; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}