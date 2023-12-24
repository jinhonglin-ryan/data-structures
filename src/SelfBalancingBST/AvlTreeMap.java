package SelfBalancingBST;

import Map.OrderedMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 * tree height is always O(lgn)
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  // return the height of a given node
  private int getHeight(Node<K, V> node) {
    if (node == null) {
      return -1; // by definition
    } else {
      return node.height;
    }
  }

  // return the balance factor of a given node
  private int getBalanceFactor(Node<K, V> node) {
    int leftHeight = getHeight(node.left);
    int rightHeight = getHeight(node.right);
    return leftHeight - rightHeight;
  }

  // update the height of a given node
  private void updateHeight(Node<K, V> node) {
    int leftHeight = getHeight(node.left);
    int rightHeight = getHeight(node.right);
    int higherHeight = Math.max(leftHeight, rightHeight);
    // curr node's height is 1 higher than the left & right child
    node.height = higherHeight + 1;
  }

  // do the right rotation and return the new root
  private Node<K, V> rightRotation(Node<K, V> node) {
    Node<K, V> leftChild = node.left;
    node.left = leftChild.right; // if the left child has a right subtree
    leftChild.right = node;
    updateHeight(node);
    updateHeight(leftChild);
    return leftChild;
  }

  // do the left rotation and return the new root
  private Node<K, V> leftRotation(Node<K, V> node) {
    Node<K, V> rightChild = node.right;
    node.right = rightChild.left; // if the right child has a left subtree
    rightChild.left = node;
    updateHeight(node);
    updateHeight(rightChild);
    return rightChild;
  }

  private Node<K, V> balanceTree(Node<K, V> node) {
    int bf = getBalanceFactor(node);
    if (bf < -1) { // imbalance node is right heavy
      if (getBalanceFactor(node.right) <= 0) { // child is right heavy -- right rotation
        node = leftRotation(node);
      } else { // child is left heavy -- right-left rotation
        node.right = rightRotation(node.right);
        node = leftRotation(node);
      }
    }
    if (bf > 1) { // imbalance node is left heavy
      if (getBalanceFactor(node.left) >= 0) { // child is left heavy -- right rotation
        node = rightRotation(node);
      } else { // child is right heavy -- left-right rotation
        node.left = leftRotation(node.left);
        node = rightRotation(node);
      }
    }
    return node;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException { // O(lgn)
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
    updateHeight(root);
    root = balanceTree(root);
  }

  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
      return new Node<K, V>(k, v);
    }
    int cmp = n.key.compareTo(k);
    if (cmp > 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp < 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }
    updateHeight(n);
    if (n.height > 1) { // only when height is > 1 could there be a likelihood that we need to balance the tree
      n = balanceTree(n);
    }
    return n;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException { // O(lgn)
    Node<K, V> n = findForSure(k);
    V value = n.value;
    root = remove(root, n);
    size--;
    return value;
  }

  // Remove node with given key from subtree rooted at given node;
  // Return changed subtree with given key missing.
  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) { // search left
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else { // cmp < 0, search right
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }
    updateHeight(subtreeRoot);
    subtreeRoot = balanceTree(subtreeRoot); // if necessary
    return subtreeRoot;
  }

  // Remove given node and return the remaining tree (structural change).
  private Node<K, V> remove(Node<K, V> node) {
    // no child or one child
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // two child: find max in the left subtree and substitute
    Node<K, V> toReplaceWith = findMax(node);
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);
    node = balanceTree(node);
    return node;
  }

  private Node<K, V> findMax(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
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
  public V get(K k) {
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
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int height;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      height = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
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

  public static void main(String[] args) {
      AvlTreeMap<Integer, String> avlTree = new AvlTreeMap<>();

      // Insert some key-value pairs
      avlTree.insert(10, "A");
      avlTree.insert(5, "B");
      avlTree.insert(15, "C");
      avlTree.insert(3, "D");
      avlTree.insert(7, "E");

      // Print the AVL tree using toString()
      System.out.println("AVL Tree:");
      System.out.println(avlTree.toString());
  }

}

