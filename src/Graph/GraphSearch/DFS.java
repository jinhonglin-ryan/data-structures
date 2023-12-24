package Graph.GraphSearch;

import java.util.LinkedList;
import java.util.Stack;

public class DFS {

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
    dfs(adjacencyList, 0);
  }

  // Function to add an edge to the graph
  private static void addEdge(LinkedList<Integer>[] adjacencyList, int source, int destination) {
    adjacencyList[source].add(destination);
  }

  // DFS traversal
  private static void dfs(LinkedList<Integer>[] adjacencyList, int startVertex) {
    boolean[] explored = new boolean[adjacencyList.length];

    // Create a stack for DFS
    Stack<Integer> stack = new Stack<>();

    // Mark the start vertex as explored and push it to the stack
    explored[startVertex] = true;
    stack.push(startVertex);

    while (!stack.isEmpty()) {
      // Pop a vertex from the top of the stack
      int currentVertex = stack.pop();
      System.out.print(currentVertex + " ");

      // Explore all neighbors of the current vertex
      for (int neighbor : adjacencyList[currentVertex]) {
        // If the neighbor is unexplored, mark it as explored and push it to the stack
        if (!explored[neighbor]) {
          explored[neighbor] = true;
          stack.push(neighbor);
        }
      }
    }
  }
}

