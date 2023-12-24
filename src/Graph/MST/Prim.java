package Graph.MST;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {
  private int vertices;
  private List<List<Edge>> graph;

  public Prim(int vertices) {
    this.vertices = vertices;
    this.graph = new ArrayList<>(vertices);
    for (int i = 0; i < vertices; i++) {
      this.graph.add(new ArrayList<>());
    }
  }

  public void addEdge(int source, int destination, int weight) {
    Edge edge1 = new Edge(destination, weight);
    Edge edge2 = new Edge(source, weight);
    graph.get(source).add(edge1);
    graph.get(destination).add(edge2);
  }

  public void primMST() {
    boolean[] visited = new boolean[vertices];
    PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

    // Start from the 0th vertex
    priorityQueue.add(new Edge(0, 0));

    while (!priorityQueue.isEmpty()) {
      Edge edge = priorityQueue.poll();
      int currentVertex = edge.destination;

      // Check if the vertex is already visited
      if (visited[currentVertex]) {
        continue;
      }

      System.out.println("Edge: " + currentVertex + " - " + edge.destination + " Weight: " + edge.weight);

      visited[currentVertex] = true;

      // Add adjacent edges to the priority queue
      for (Edge adjacentEdge : graph.get(currentVertex)) {
        if (!visited[adjacentEdge.destination]) {
          priorityQueue.add(adjacentEdge);
        }
      }
    }
  }

  public static void main(String[] args) {
    Prim graph = new Prim(5);

    // Adding edges
    graph.addEdge(0, 1, 2);
    graph.addEdge(0, 3, 3);
    graph.addEdge(1, 2, 5);
    graph.addEdge(1, 3, 1);
    graph.addEdge(2, 4, 4);
    graph.addEdge(3, 4, 6);

    // Finding and printing the Minimum Spanning Tree
    System.out.println("Minimum Spanning Tree:");
    graph.primMST();
  }
}
