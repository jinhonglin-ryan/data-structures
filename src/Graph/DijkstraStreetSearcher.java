package Graph;


import java.util.*;


public class DijkstraStreetSearcher extends StreetSearcher {

  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
  }

  @Override
  public void findShortestPath(String startName, String endName) {

    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);

    if (!isValid(start, startName, end, endName)) {
      return;
    }

    double totalDist = -1;  // totalDist must be update below

    Map<Vertex<String>, Double> distance = new HashMap<>(); // map btw a vertex and the distance to the source
    Map<Vertex<String>, Boolean> explored = new HashMap<>();
    // the priority queue is ordered by the ascending order of distance
    PriorityQueue<Vertex<String>> pq = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

    initializeDijkstra(start, distance, explored, pq);
    totalDist = dijkstraAlgorithm(start, end, distance, explored, pq);

    // These method calls will create and print the path for you
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }

  private boolean isValid(Vertex<String> start, String startName, Vertex<String> end, String endName) {
    if (start == null) {
      System.out.println("Invalid Endpoint: " + startName);
      return false;
    }
    if (end == null) {
      System.out.println("Invalid Endpoint: " + endName);
      return false;
    }
    return true;
  }

  private double dijkstraAlgorithm(Vertex<String> start, Vertex<String> end, Map<Vertex<String>, Double> distance,
                                Map<Vertex<String>, Boolean> explored, PriorityQueue<Vertex<String>> pq) {
    pq.add(start);
    while (!pq.isEmpty()) {
      Vertex<String> curr = pq.poll(); // get the shortest distance vertex
      if (explored.get(curr)) { // if we have explored the vertex
        continue;
      }

      explored.put(curr, true); // we now have explored the vertex
      if (curr.equals(end)) {
        break; // we have reached the end vertex, exit early
      }

      for (Edge<String> e : graph.outgoing(curr)) {
        Vertex<String> next = graph.to(e);
        //        if (explored.get(next)) {
        //          continue; // if this neighbor is explored
        //        }

        double nextDis = distance.get(curr) + (double) graph.label(e);
        dijkstraHelper(next, e, nextDis, distance, pq);

      }
    }
    return distance.get(end);
  }
  
  private void dijkstraHelper(Vertex<String> next, Edge<String> e, double nextDis,
                              Map<Vertex<String>, Double> distance, PriorityQueue<Vertex<String>> pq) {
    if (nextDis < distance.get(next)) {
      distance.put(next, nextDis);
      graph.label(next, e);
      pq.add(next);
    }
  }

  private void initializeDijkstra(Vertex<String> start, Map<Vertex<String>, Double> distance,
                                  Map<Vertex<String>, Boolean> explored, PriorityQueue<Vertex<String>> pq) {
    for (Vertex<String> v : vertices.values()) {
      distance.put(v, MAX_DISTANCE);
      explored.put(v, false);
    }
    distance.put(start, 0.0);
  }


}
