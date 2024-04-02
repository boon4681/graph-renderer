package visualizer.logic.prims;

import java.util.*;

import visualizer.logic.Edge;

/**
 * The PrimsTree class represents a graph and provides methods to find the
 * Minimum Spanning Tree (MST)
 * using Prim's algorithm.
 */

public class Prims {
    private final int MAX_VALUE = Integer.MAX_VALUE;
    private Map<Integer, List<Edge>> graph = new HashMap<>();

    public static record PrimsRecord(int from, int to) {
        @Override
        public final String toString() {
            return from + " -> " + to;
        }
    }

    public void addEdge(int start, int end, int weight) {
        List<Edge> edgeList = graph.getOrDefault(start, new ArrayList<>());
        edgeList.add(new Edge(end, weight));
        graph.put(start, edgeList);
        edgeList = graph.getOrDefault(end, new ArrayList<>());
        edgeList.add(new Edge(start, weight));
        graph.put(end, edgeList);
    }

    public Stack<PrimsRecord> run(int startVertex) {
        boolean[] visited = new boolean[graph.size()];
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> {
            int weightDiff = a.weight - b.weight;
            if (weightDiff == 0) {
                return a.v - b.v; // Ensure least dest is chosen for same weight
            }
            return weightDiff;
        });
        int[] dist = new int[graph.size()];
        Arrays.fill(dist, MAX_VALUE);
        int[] parent = new int[graph.size()];
        Arrays.fill(parent, -1);

        dist[startVertex] = 0;
        pq.offer(new Edge(startVertex, 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().v;
            if (visited[u]) {
                continue;
            }
            visited[u] = true;
            List<Edge> edgeList = graph.get(u);
            if (edgeList != null) {
                for (Edge edge : edgeList) {
                    int v = edge.v;
                    int weight = edge.weight;
                    if (!visited[v] && dist[v] > weight) {
                        dist[v] = weight;
                        parent[v] = u;
                        pq.offer(new Edge(v, weight));
                    }
                }
            }
        }
        Stack<PrimsRecord> stack = new Stack<>();
        // int totalWeight = 0;
        for (int i = 0; i < graph.size(); i++) {
            if (parent[i] != -1) {
                stack.push(new PrimsRecord(parent[i], i));
                // System.out.println(parent[i] + " --> " + i + " (weight: " + weight + ")");
                // totalWeight += weight;
            }
        }
        return stack;
        // System.out.println("Total weight of the Minimum Spanning Tree: " +
        // totalWeight);
    }
}