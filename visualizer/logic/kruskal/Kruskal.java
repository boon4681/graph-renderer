package visualizer.logic.kruskal;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Comparator;
import java.util.List;

import visualizer.logic.Edge;

/**
 * The KruskalTree class represents a graph and provides methods to find the
 * Minimum Spanning Tree (MST)
 * using Kruskal's algorithm.
 */
public class Kruskal {

    public static record KruskalRecord(int from, int to) {
        @Override
        public final String toString() {
            return from + " -> " + to;
        }
    }

    // Defines subset element structure
    private class Subset {
        int parent, rank;

        public Subset(int parent, int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    private List<Edge> graphEdges;

    public Kruskal() {
        graphEdges = new ArrayList<>();
    }

    // Function to add an edge to the graph
    public void addEdge(int src, int dest, int weight) {
        Edge edge = new Edge(src, dest, weight);
        graphEdges.add(edge);
    }

    // Function to run Kruskal's algorithm
    public Stack<KruskalRecord> run(int vertices) {
        int V = vertices;

        // Sort the edges in non-decreasing order
        // (increasing with repetition allowed)
        graphEdges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        });

        return kruskals(V, graphEdges);
    }

    // Function to find the MST
    private Stack<KruskalRecord> kruskals(int V, List<Edge> edges) {
        int j = 0;
        int noOfEdges = 0;

        Stack<KruskalRecord> records = new Stack<>();

        // Allocate memory for creating V subsets
        Subset subsets[] = new Subset[V];

        // Allocate memory for results
        Edge results[] = new Edge[V];

        // Create V subsets with single elements
        for (int i = 0; i < V; i++) {
            subsets[i] = new Subset(i, 0);
        }

        // Number of edges to be taken is equal to V-1
        while (noOfEdges < V - 1) {

            // Pick the smallest edge. And increment
            // the index for next iteration
            Edge nextEdge = edges.get(j);
            int x = findRoot(subsets, nextEdge.start);
            int y = findRoot(subsets, nextEdge.end);

            // If including this edge doesn't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                results[noOfEdges] = nextEdge;
                union(subsets, x, y);
                noOfEdges++;
            }

            j++;
        }

        for (int i = 0; i < noOfEdges; i++) {
            records.push(new KruskalRecord(results[i].start, results[i].end));
        }
        return records;
    }

    // Function to unite two disjoint sets
    private void union(Subset[] subsets, int x, int y) {
        int rootX = findRoot(subsets, x);
        int rootY = findRoot(subsets, y);

        if (subsets[rootY].rank < subsets[rootX].rank) {
            subsets[rootY].parent = rootX;
        } else if (subsets[rootX].rank < subsets[rootY].rank) {
            subsets[rootX].parent = rootY;
        } else {
            subsets[rootY].parent = rootX;
            subsets[rootX].rank++;
        }
    }

    // Function to find parent of a set
    private int findRoot(Subset[] subsets, int i) {
        if (subsets[i].parent == i)
            return subsets[i].parent;

        subsets[i].parent = findRoot(subsets, subsets[i].parent);
        return subsets[i].parent;
    }
}
