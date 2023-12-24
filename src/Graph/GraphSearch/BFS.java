package Graph.GraphSearch;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {

  public static void main(String[] args) {
    // Example graph represented by an adjacency list
    int vertices = 6;
    LinkedList<Integer>[] adjacencyList = new LinkedList[vertices];

    for (int i = 0; i < vertices; i++) {
      adjacencyList[i] = new LinkedList<>();
    }

    // Adding edges to the graph
    addEdge(adjacencyList, 0, 1);
    addEdge(adjacencyList, 0, 2);
    addEdge(adjacencyList, 1, 3);
    addEdge(adjacencyList, 1, 4);
    addEdge(adjacencyList, 2, 5);


    // Starting BFS from vertex 0
    bfs(adjacencyList, 0);
  }

  // Function to add an edge to the graph
  private static void addEdge(LinkedList<Integer>[] adjacencyList, int source, int destination) {
    adjacencyList[source].add(destination);
  }

  // BFS traversal
  private static void bfs(LinkedList<Integer>[] adjacencyList, int startVertex) {
    boolean[] explored = new boolean[adjacencyList.length];

    // Create a queue for BFS
    Queue<Integer> queue = new LinkedList<>();

    // Mark the start vertex as explored and enqueue it
    explored[startVertex] = true;
    queue.add(startVertex);

    while (!queue.isEmpty()) {
      // Dequeue a vertex from the front of the queue
      int currentVertex = queue.poll();
      System.out.print(currentVertex + " ");

      // Explore all neighbors of the current vertex
      for (int neighbor : adjacencyList[currentVertex]) {
        // If the neighbor is unexplored, mark it as explored and enqueue it
        if (!explored[neighbor]) {
          explored[neighbor] = true;
          queue.add(neighbor);
        }
      }
    }
  }
}
