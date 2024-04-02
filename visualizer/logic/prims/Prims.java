package visualizer.logic.prims;

import java.util.*;

import visualizer.logic.Edge;

/**
 * The PrimsTree class represents a graph and provides methods to find the
 * Minimum Spanning Tree (MST)
 * using Prim's algorithm.
 */

public class Prims {
    int vertices;
    List<List<Edge>> adjacencyList;

    public Prims(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int start, int end, int weight) {
        Edge edge = new Edge(start, end, weight);
        adjacencyList.get(start).add(edge);
        adjacencyList.get(end).add(edge);
    }

    public void run(int startVertex) {
        boolean[] visited = new boolean[vertices];
        int[] parent = new int[vertices];
        int[] key = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            key[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>(vertices, Comparator.comparingInt(e -> e.weight));
        key[startVertex] = 0;
        pq.offer(new Edge(-1, startVertex, 0));

        while (!pq.isEmpty()) {
            Edge minEdge = pq.poll();
            int u = minEdge.end;

            if (visited[u]) {
                continue;
            }

            visited[u] = true;

            for (Edge edge : adjacencyList.get(u)) {
                int v = edge.start == u ? edge.end : edge.start;
                int weight = edge.weight;

                if (!visited[v] && weight < key[v]) {
                    parent[v] = u;
                    key[v] = weight;
                    pq.offer(new Edge(u, v, weight));
                }
            }
        }
        int weightSummed = 0;
        for (int i = 1; i < vertices; i++) {
            weightSummed += key[i];
            System.out.println(parent[i] + " -> " + i + " weight is " + key[i]);
        }
        System.out.println("Total cost of MST: " + weightSummed);
    }
}