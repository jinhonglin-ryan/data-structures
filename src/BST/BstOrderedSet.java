package BST;

import java.util.*;

/**
 * A Binary Search Tree implementation of OrderedSet ADT.
 *
 * @param <T> Element type.
 */
public class BstOrderedSet<T extends Comparable<T>> implements OrderedSet<T> {

  private Node<T> root;
  private int numElements;

  /**
   * Construct an empty BstOrderedSet.
   */
  public BstOrderedSet() {
    root = null;
    numElements = 0;
  }

  @Override
  // implement insert recursively
  public void insert(T t) {
    // use a recursive helper insert
    root = insert(root, t);
  }

  /*
  inserts given value into the subtree rooted at the given node
  and returns the changed subtree with the new node added
   */
  private Node<T> insert(Node<T> node, T data) {
    if (node == null) {
      // if the current tree is empty, return a new node;
      numElements++;
      return new Node<>(data);
    }

    // otherwise, recur down the tree 向下递归
    if (node.data.compareTo(data) > 0) {
      node.left = insert(node.left, data);
    } else if (node.data.compareTo(data) < 0) {
      node.right = insert(node.right, data);
    }

    // return the unchanged node pointer
    return node;
  }

  //  // implement insert iteratively
  //  public void insert(T t) {
  //    if (root == null) { // if the BST hasn't been created yet
  //      root = new Node<>(t);
  //      return;
  //    }
  //
  //    Node<T> curr = root;
  //    while (curr != null) {
  //      if (curr.data.compareTo(t) > 0) { // go to the left subtree
  //        if (curr.left == null) { // reached a leaf
  //          // directly insert the new Node
  //          curr.left = new Node<>(t);
  //          curr = null; // break out of the loop
  //        } else { // explore the left subtree
  //          curr = curr.left;
  //        }
  //      } else if (curr.data.compareTo(t) < 0) { // go to the right subtree
  //        if (curr.right == null) { //reached a leaf
  //          // insert the new node directly
  //          curr.right = new Node<>(t);
  //          curr = null; // break out of the loop
  //        } else { // explore the right subtree
  //          curr = curr.right;
  //        }
  //
  //      } else { // where curr.data.compareTo(t) = 0
  //        // we don't allow duplicate values in the BST
  //        return;
  //      }
  //    }
  //  }

  @Override
  public void remove(T t) {
    // Uses a recursive (private) helper remove
    root = remove(root, t);
  }

  private Node<T> remove(Node<T> node, T t) {
    if (node == null) { // base case: no t in the BST
      return null;
    }

    // find the node that contains "t"
    if (root.data.compareTo(t) > 0) {
      node.left = remove(node.left, t);
    } else if (root.data.compareTo(t) < 0) {
      node.right = remove(node.right, t);
    } else { // found the t, start removing

      // zero or one child
      // if no child, code will return null in the first if, so the leaf is removed
      if (node.right == null) {
        numElements--;
        return node.left;
      } else if (node.left == null) {
        numElements--;
        return node.right;
      }

      // two children
      Node<T> next = findSmallest(node.right);
      node.data = next.data;
      node.right = remove(node.right, next.data);
      numElements--;
    }

    return node;
  }

  // find the smallest value in subtree rooted at node
  // Pre: node != null
  private Node<T> findSmallest(Node<T> node) {
    Node<T> small = node;
    while (small.left != null) {
      // go left as far as we can
      small = small.left;
    }
    return small;
  }

  @Override
  public boolean has(T t) {
    // Node<T> target = find(t);
    Node<T> target = find(root, t);
    return target != null;
  }

  // implement find iteratively
  //  private Node<T> find(T t) {
  //    Node<T> curr = root;
  //    while (curr != null) {
  //      if (curr.data.compareTo(t) > 0) {
  //        curr = curr.left;
  //      } else if (curr.data.compareTo(t) < 0) {
  //        curr = curr.right;
  //      } else {
  //        return curr;
  //      }
  //    }
  //    return curr;
  //  }

  // implement find recursively
  private Node<T> find(Node<T> node, T t) {
    if (node == null) {
      return null;
    }

    if (node.data.compareTo(t) > 0) {
      return find(node.left, t);
    } else if (node.data.compareTo(t) < 0) {
      return find(node.right, t);
    } else {
      return node;
    }
  }

  // more polished recursive find()
  //  private Node<T> find(Node<T> node, T t) {
  //    if (node == null || node.data.equals(t)) {
  //      return node;
  //    }
  //
  //    if (node.data.compareTo(t) > 0) {
  //      return find(node.left, t);
  //    }
  //
  //    // if node.data.compareTo(t) < 0
  //    return find(node.right, t);
  //  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Iterator<T> iterator() {
    return new InorderIterator();
  }

  public Iterator<T> postOrderIterator() {
    return new PostOrderIterator();
  }

  public Iterator<T> preOrderIterator() {
    return new PreOrderIterator();
  }

  private class InorderIterator implements Iterator<T> {
    private final Stack<Node<T>> stack;

    private InorderIterator() {
      this.stack = new Stack<>();
      pushLeft(root); // 将根节点和所有的左子树依次推入栈
    }

    private void pushLeft(Node<T> curr) {
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
    public T next() { //第一次iteration，先将最左的子树推出，然后推入其右子树
      Node<T> top = stack.pop();
      pushLeft(top.right);
      return top.data;
    }
  }

  /*
  * Post-order/Pre-order Traversal (Iterator Class)
  * use Queue
  * post-order: left, right, root
  * pre_order: root, left, right
  */

  private class PostOrderIterator implements Iterator<T> {
    private final Queue<T> postOrder;

    PostOrderIterator() {
      postOrder = new ArrayDeque<>();
      fillQueuePostOrder(root, postOrder);
    }

    // Recursively fill the queue with the post-order traversal of the BST.
    private void fillQueuePostOrder(Node<T> node, Queue<T> queue) {
      if (node != null) {
        fillQueuePostOrder(node.left, queue);
        fillQueuePostOrder(node.right, queue);
        queue.add(node.data);
      }
    }

    @Override
    public boolean hasNext() {
      return !postOrder.isEmpty();
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return postOrder.remove();
    }
  }

  private class PreOrderIterator implements Iterator<T> {
    private final Queue<T> preOrder;

    PreOrderIterator() {
      preOrder = new ArrayDeque<>();
      fillQueuePreOrder(root, preOrder);
    }

    // Recursively fill the queue with the pre-order traversal of the BST.
    private void fillQueuePreOrder(Node<T> node, Queue<T> queue) {
      if (node != null) {
        queue.add(node.data);
        fillQueuePreOrder(node.left, queue);
        fillQueuePreOrder(node.right, queue);
      }
    }

    @Override
    public boolean hasNext() {
      return !preOrder.isEmpty();
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return preOrder.remove();
    }
  }

  private static class Node<E> {
    E data;
    Node<E> left; // points to the root node of the left subtree
    Node<E> right; // points to the root node of the right subtree

    Node(E data) {
      this.data = data;
    }
  }

  /**
   * Driver program to visualize/test this implementation.
   *
   * @param args command-line arguments.
   */
  public static void main(String[] args) {
    BstOrderedSet<Integer> bst = new BstOrderedSet<>();
    bst.insert(7);
    bst.insert(4);
    bst.insert(13);
    bst.insert(1);
    bst.insert(6);
    bst.insert(10);
    bst.insert(15);
    bst.insert(3);

    System.out.println(bst.size());
    System.out.println(bst.has(13));
    System.out.println(bst.has(23));

    // in-order
    for (int i : bst) {
      System.out.print(i + " ");
    }
    System.out.println();


    /*
                  7
            4           13
         1     6     10     15
           3

    中序遍历
    1. InorderIterator的stack初始化后，有[7,4,1]
    2. 第一次遍历，推出1，然后pushLeft(1的右子树：3)，所以stack为[7,4,3]
    3. 第二次遍历，推出3，然后pushLeft(3的右子树：null), stack为[7,4]
    4. 第三次遍历，推出4，然后pushLeft(4的右子树：6)，stack为[7,6]
    5. 第四次遍历，推出6，然后pushLeft(6的右子树：null), stack为[7]
    6. 第五次遍历，推出7，然后pushLeft(7的右子树：13), stack为[13,10]
    7. 第六次遍历，推出10，然后pushLeft(10的右子: null), stack为[13]
    8. 第七次遍历，推出13，然后pushLeft(13的右子树: 15), stack为[15]
    8. 第八次遍历，推出15，然后pushLeft(15的右子树: null), stack为[]
     */

    // post-order
    Iterator<Integer> postOrder = bst.postOrderIterator();
    while (postOrder.hasNext()) {
      System.out.print(postOrder.next() + " ");
    }
    System.out.println();

    // pre-order
    Iterator<Integer> preOrder = bst.preOrderIterator();
    while (preOrder.hasNext()) {
      System.out.print(preOrder.next() + " ");
    }
    System.out.println();

    /*
                  7
            4           13
         1     6     10     15
           3

     */
  }


}
