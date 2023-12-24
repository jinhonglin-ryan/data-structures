package Graph.MST;

class Edge implements Comparable<Edge> {
  int destination;
  int weight;

  public Edge(int destination, int weight) {
    this.destination = destination;
    this.weight = weight;
  }

  @Override
  public int compareTo(Edge other) {
    return Integer.compare(this.weight, other.weight);
  }
}
