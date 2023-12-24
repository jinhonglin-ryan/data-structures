package Graph;

import Graph.exceptions.InsertionException;
import Graph.exceptions.PositionException;
import Graph.exceptions.RemovalException;

import java.util.Collections;
import java.util.HashSet;

/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {


  private HashSet<VertexNode<V>> vertices;
  private HashSet<EdgeNode<E>> edges;

  /**
   * Default Constructor for SparseGraph: Create a SparseGraph.
   */
  public SparseGraph() {
    this.vertices = new HashSet<>();
    this.edges = new HashSet<>();
  }

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts the edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    // if v is null
    if (v == null) {
      throw new InsertionException();
    }

    VertexNode<V> vertex = new VertexNode<>(v);
    vertex.owner = this;

    // if v is already in the graph
    if (vertices.contains(vertex)) { // need to rewrite equals() and hashCode() in VertexNode Class
      throw new InsertionException();
    }

    // else, v is valid, add into our collection
    vertices.add(vertex);

    return vertex;
  }

  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {
    // check if from or to is null
    VertexNode<V> fromVertex = convert(from);
    VertexNode<V> toVertex = convert(to);

    // check if it creates a self loop
    if (fromVertex.equals(toVertex)) {
      throw new InsertionException();
    }

    // check if it creates duplicate edges
    for (EdgeNode<E> outgoingEdge : fromVertex.out) {
      if (outgoingEdge.to.equals(toVertex)) {
        throw new InsertionException();
      }
    }

    // else, valid, insert into our collection
    EdgeNode<E> edge = new EdgeNode<>(fromVertex, toVertex, e);

    // update
    fromVertex.out.add(edge);
    toVertex.in.add(edge);
    edges.add(edge);
    edge.owner = this;

    return edge;
  }

  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    // check if vertex position is valid
    VertexNode<V> vertexToRemove = convert(v);

    // if the set does not have the node to remove, throw PositionException
    if (!vertices.contains(vertexToRemove)) {
      throw new PositionException();
    }

    // if the vertex still has incident edges, throw RemovalException
    if (!(vertexToRemove.in.isEmpty()) || !(vertexToRemove.out.isEmpty())) {
      throw new RemovalException();
    }

    vertices.remove(vertexToRemove);
    vertexToRemove.owner = null;
    return vertexToRemove.data;
  }

  @Override
  public E remove(Edge<E> e) throws PositionException {
    // check if the edge is valid
    EdgeNode<E> edgeToRemove = convert(e);

    // update
    edgeToRemove.from.out.remove(edgeToRemove);
    edgeToRemove.to.in.remove(edgeToRemove);
    edges.remove(edgeToRemove);
    edgeToRemove.owner = null;

    return edgeToRemove.data;
  }

  @Override
  public Iterable<Vertex<V>> vertices() {

    return Collections.unmodifiableSet(vertices);
  }

  @Override
  public Iterable<Edge<E>> edges() {

    return Collections.unmodifiableSet(edges);
  }

  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    // check if vertex position is valid
    VertexNode<V> vertex = convert(v);

    // if vertex is not in the graph, throw PositionException
    if (!vertices.contains(vertex)) {
      throw new PositionException();
    }

    return Collections.unmodifiableSet(vertex.out);
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    // check if vertex position is valid
    VertexNode<V> vertex = convert(v);

    // if vertex is not in the graph, throw PositionException
    if (!vertices.contains(vertex)) {
      throw new PositionException();
    }

    return Collections.unmodifiableSet(vertex.in);
  }

  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    // check if the edge is valid
    EdgeNode<E> edge = convert(e);

    // if the edge is not in the graph, throw PositionException
    if (!edges.contains(edge)) {
      throw new PositionException();
    }

    return edge.from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    // check if the edge is valid
    EdgeNode<E> edge = convert(e);

    // if the edge is not in the graph, throw PositionException
    if (!edges.contains(edge)) {
      throw new PositionException();
    }

    return edge.to;
  }

  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    // check if v is null
    VertexNode<V> vertex = convert(v);

    // check if the vertex is not in the graph
    if (!vertices.contains(vertex)) {
      throw new PositionException();
    }

    // else, the vertex is valid
    vertex.label = l;
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    // check if e is null
    EdgeNode<E> edge = convert(e);

    // check if the edge is not in the graph
    if (!edges.contains(edge)) {
      throw new PositionException();
    }

    // else, the edge is valid
    edge.label = l;
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    // check if v is null
    VertexNode<V> vertex = convert(v);

    // check if v is in the graph
    if (!vertices.contains(vertex)) {
      throw new PositionException();
    }
    return vertex.label;
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    // check if e is null
    EdgeNode<E> edge = convert(e);

    // check if e is in the graph
    if (!edges.contains(edge)) {
      throw new PositionException();
    }
    return edge.label;
  }

  @Override
  public void clearLabels() {
    for (VertexNode<V> v : vertices) {
      v.label = null;
    }
    for (EdgeNode<E> e : edges) {
      e.label = null;
    }
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<V, E>(this);
    return gp.toString();
  }

  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;

    HashSet<EdgeNode<E>> out;
    HashSet<EdgeNode<E>> in;

    VertexNode(V v) {
      this.data = v;
      this.label = null;
      out = new HashSet<>();
      in = new HashSet<>();
    }


    @Override
    public V get() {
      return this.data;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true; // Same object reference, they are equal
      }

      if (obj == null || getClass() != obj.getClass()) {
        return false; // Not the same class or obj is null, they are not equal
      }

      VertexNode<V> v = (VertexNode<V>) obj; // Now it's safe to cast
      return this.data.equals(v.data);
    }

    @Override
    public int hashCode() {
      return this.data.hashCode();
    }
  }

  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;


    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    @Override
    public E get() {
      return this.data;
    }
  }
}
