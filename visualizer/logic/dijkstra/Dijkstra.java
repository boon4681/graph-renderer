package visualizer.logic.dijkstra;

import java.util.*;

import visualizer.logic.Edge;

public class Dijkstra {
    private int V;
    private List<List<Edge>> adj;

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

    public void dijkstra(int start) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (Edge edge : adj.get(u)) {
                int v = edge.v;
                int weight = edge.weight;
                if (!visited[v] && dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
                    dist[v] = dist[u] + weight;
            }
        }

        printSolution(dist);
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

    private void printSolution(int[] dist) {
        System.out.println("Dijkstra: ");
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }
}