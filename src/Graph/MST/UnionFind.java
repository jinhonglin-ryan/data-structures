package Graph.MST;

public class UnionFind {
  private int[] parent;
  private int[] size;

  public UnionFind(int n) {
    parent = new int[n];
    size = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
      size[i] = 1;
    }
  }

  public int findRoot(int vertex) {
    if (parent[vertex] != vertex) {
      parent[vertex] = findRoot(parent[vertex]); // Path compression
    }
    return parent[vertex];
  }

  public void union(int root1, int root2) {
    if (size[root1] < size[root2]) {
      parent[root1] = root2;
      size[root2] += size[root1];
    } else {
      parent[root2] = root1;
      size[root1] += size[root2];
    }
  }

  public boolean isConnected(int vertex1, int vertex2) {
    return findRoot(vertex1) == findRoot(vertex2);
  }
}

