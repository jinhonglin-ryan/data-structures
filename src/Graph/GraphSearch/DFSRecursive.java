package Graph.GraphSearch;

import java.util.LinkedList;

public class DFSRecursive {

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

    // Starting DFS from vertex 0
    boolean[] visited = new boolean[vertices];
    recursiveDFS(adjacencyList, 0, visited);
  }

  // Function to add an edge to the graph
  private static void addEdge(LinkedList<Integer>[] adjacencyList, int source, int destination) {
    adjacencyList[source].add(destination);
  }

  // Recursive DFS traversal
  private static void recursiveDFS(LinkedList<Integer>[] adjacencyList, int currentVertex, boolean[] visited) {
    // Mark the current vertex as visited
    visited[currentVertex] = true;
    System.out.print(currentVertex + " ");

    // Explore all neighbors of the current vertex
    for (int neighbor : adjacencyList[currentVertex]) {
      // If the neighbor is unvisited, recursively call DFS on it
      if (!visited[neighbor]) {
        recursiveDFS(adjacencyList, neighbor, visited);
      }
    }
  }
}
