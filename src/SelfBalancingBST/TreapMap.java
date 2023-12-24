package SelfBalancingBST;

import Map.OrderedMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Map implemented as a Treap.
 * Explanation for Treap:
 * Each entry (key-value pair) is assigned a random priority.
 * The BST ordering property is based on keys.
 * The priorities are used to create a (min or max) heap, here we have min heap
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class TreapMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'rand'. ***/
  private static Random rand;
  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  /**
   * Make a TreapMap.
   */
  public TreapMap() {
    rand = new Random();
  }

  /**
   * Make a TreapMap with random seed specified.
   * @param seed specific seed number for generating a sequence random numbers
   */
  public TreapMap(int seed) {
    rand = new Random(seed);
  }

  private Node<K, V> rightRotation(Node<K, V> node) {
    Node<K, V> leftChild = node.left;
    node.left = leftChild.right;
    leftChild.right = node;
    return leftChild;
  }

  private Node<K, V> leftRotation(Node<K, V> node) {
    Node<K, V> rightChild = node.right;
    node.right = rightChild.left;
    rightChild.left = node;
    return rightChild;
  }

  /*
  Insertion
  1. Generate random priority for the entry (key-value pair).
  2. Insert the entry as you would in BST (based on the "key" and ignoring priorities)
  3. If priorities (inserted node and its parent) are not in the desired order (based on whether we maintain a max- or min-heap), rotate node up and parent down (instead of swim-up).
  4. Repeat the last step until all priorities are in the desired order.
  */
  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  private Node<K, V> insert(Node<K, V> node, K k, V v) {
    if (node == null) { // if we have reached the palace to insert the node
      return new Node<K, V>(k, v);
    }

    int cmp = node.key.compareTo(k);
    if (cmp < 0) {
      node.right = insert(node.right, k, v);
      if (node.right.priority < node.priority) { // maintain the min heap structure
        node = leftRotation(node);
      }
    } else if (cmp > 0) {
      node.left = insert(node.left, k, v);
      if (node.left.priority < node.priority) { // maintain the min heap structure
        node = rightRotation(node);
      }
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }
    return node;
  }

  /*
  Removal
  1. Find the target following a "lookup" in BST (on keys, ignoring priorities).
  2. Change the priority of the entry to be removed to a value that results in the entry sinking.
      here in the min heap, we will make the priority of node to remove to be the max value
  3. Rotate down the target until it cannot sink any further (it becomes a leaf), then remove it.
  */
  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    node.priority = Integer.MAX_VALUE; // assign the node to be removed with the highest priority as we are in min heap
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  // Remove node with given key from subtree rooted at given node;
  // Return changed subtree with given key missing.
  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else if (cmp < 0) {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    } else { // cmp == 0
      return remove(subtreeRoot);
    }
    return subtreeRoot;
  }

  private Node<K, V> remove(Node<K, V> node) {
    // 0 or 1 child
    if (node.left == null) {
      return node.right;
    } else if (node.right == null) {
      return node.left;
    }

    // 2 child
    if (node.left.priority < node.right.priority) {
      node = rightRotation(node);
      node.right = remove(node.right);
    } else { // right node has a smaller priority
      node = leftRotation(node);
      node.left = remove(node.left);
    }
    return node;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  // Return node for given key,
  // throw an exception if the key is not in the tree.
  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    return n.value;
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

  // Return node for given key.
  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }


  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   * Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers. Since this is
   * a node class for a Treap we also include a priority field.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int priority;

    // Constructor to make node creation easier to read.
    // Generate each entry and assign a random priority
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      priority = generateRandomInteger();
    }

    // Use this function to generate random values
    // to use as node priorities as you insert new
    // nodes into your TreapMap.
    private int generateRandomInteger() {
      // Note: do not change this function!
      return rand.nextInt();
    }

    @Override
    public String toString() {
      return key + ":" + value + ":" + priority;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }
  }

  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    private InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.empty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }
}
