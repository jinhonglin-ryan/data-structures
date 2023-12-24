package Graph.MST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Kruskal {
  private int vertices;
  private List<Edge> edges;

  public Kruskal(int vertices) {
    this.vertices = vertices;
    this.edges = new ArrayList<>();
  }

  public void addEdge(int source, int destination, int weight) {
    edges.add(new Edge(destination, weight));
    edges.add(new Edge(source, weight));
  }

  public List<Edge> kruskalMST() {
    List<Edge> result = new ArrayList<>();
    Collections.sort(edges);

    UnionFind unionFind = new UnionFind(vertices);

    for (Edge edge : edges) {
      int rootSource = unionFind.findRoot(edge.destination);
      int rootDestination = unionFind.findRoot(edge.destination);

      if (!unionFind.isConnected(rootSource, rootDestination)) {
        result.add(edge);
        unionFind.union(rootSource, rootDestination);
      }
    }

    return result;
  }

  public static void main(String[] args) {
    Kruskal graph = new Kruskal(5);

    // Adding edges
    graph.addEdge(0, 1, 2);
    graph.addEdge(0, 3, 3);
    graph.addEdge(1, 2, 5);
    graph.addEdge(1, 3, 1);
    graph.addEdge(2, 4, 4);
    graph.addEdge(3, 4, 6);

    // Finding and printing the Minimum Spanning Tree
    System.out.println("Minimum Spanning Tree:");
    List<Edge> mst = graph.kruskalMST();
    for (Edge edge : mst) {
      System.out.println("Edge: " + edge.destination + " - " + edge.weight);
    }
  }
}
