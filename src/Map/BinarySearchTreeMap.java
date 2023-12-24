package Map;

import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTreeMap<K extends Comparable<K>, V>
    implements OrderedMap<K, V> {

  private static class Node<K, V> {
    K key;
    V value;
    Node<K, V> left;
    Node<K, V> right;

    Node(K k, V v) {
      key = k;
      value = v;
    }
  }

  private Node<K, V> root;
  private int size;

  public BinarySearchTreeMap() {
    this.root = null;
    this.size = 0;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  private Node<K, V> insert(Node<K, V> node, K k, V v) {
    if (node == null) {
      return new Node<>(k, v);
    }

    int cmp = k.compareTo(node.key);
    if (cmp > 0) {
      node.right = insert(node.right, k, v);
    } else if (cmp < 0) {
      node.left = insert(node.left, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }
    return node;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    root = remove(root, k);
    return node.value;
  }

  private Node<K, V> remove(Node<K, V> node, K k) {
    if (node == null) {
      return node;
    }
    int cmp = node.key.compareTo(k);
    if (cmp == 0) { // found the node to remove
      // zero or one child
      if (node.left == null) {
        size--;
        return node.right;
      } else if (node.right == null) {
        size--;
        return node.left;
      }
      // two children
      Node<K, V> small = findSmallest(node.right);
      node.key = small.key;
      node.value = small.value;
      node.right = remove(node.right, small.key);
      size--;
      return node;
    } else if (cmp > 0) {
      node.left = remove(node.left, k);
    } else { // cmp < 0
      node.right = remove(node.right, k);
    }
    return node;
  }

  private Node<K, V> findSmallest(Node<K, V> node) {
    Node<K, V> curr = node;
    while (curr.left != null) {
      curr = curr.left;
    }
    return curr;
  }

  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> node = root;
    while (node != null) {
      int cmp = node.key.compareTo(k);
      if (cmp > 0) {
        node = node.left;
      } else if (cmp < 0) {
        node = node.right;
      } else {
        return node;
      }
    }
    return null;
  }

  private Node<K, V> findForSure(K k) {
    Node<K, V> node = find(k);
    if (node == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return node;
  }
  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    node.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    return node.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }
    private void pushLeft(Node<K,V> node) {
      Node<K, V> curr = node;
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }
    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }
}

