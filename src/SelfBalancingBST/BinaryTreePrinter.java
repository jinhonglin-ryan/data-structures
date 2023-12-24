package SelfBalancingBST;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class that prints binary trees based on a level-order traversal that
 * provides an easy way to understand view of the tree structure.
 */
public class BinaryTreePrinter {

  /**
   * Breadth first traversal that prints binary tree out level by level.
   * Each existing node is printed via its toString() representation
   * Non-existing nodes are printed as 'null'.
   * There is a space between all nodes at the same level.
   * The levels of the binary tree are separated by new lines.
   * Returns empty string if the root is null.
   *
   * @param root top node of binary tree to start printing from
   * @return binary tree string serialization
   **/
  public static String printBinaryTree(BinaryTreeNode root) {
    StringBuilder s = new StringBuilder();
    Queue<BinaryTreeNode> q = new LinkedList<>();

    q.add(root);
    boolean onlyNullChildrenAdded = root == null;
    while (!q.isEmpty() && !onlyNullChildrenAdded) {
      onlyNullChildrenAdded = true;

      int levelSize = q.size();
      while (levelSize-- > 0) {
        boolean nonNullChildAdded = handleNextNodeToString(q, s);
        if (nonNullChildAdded) {
          onlyNullChildrenAdded = false;
        }
        s.append(" ");
      }

      s.deleteCharAt(s.length() - 1);
      s.append("\n");
    }

    return s.toString();
  }

  // Helper function for toString() to build String for a single node
  // and add its children to the queue.
  // Returns true if a non-null child was added to the queue, false otherwise
  private static boolean handleNextNodeToString(
      Queue<BinaryTreeNode> q,
      StringBuilder s) {

    BinaryTreeNode n = q.remove();
    if (n != null) {
      s.append(n);

      BinaryTreeNode left = n.getLeftChild();
      BinaryTreeNode right = n.getRightChild();
      q.add(left);
      q.add(right);

      return left != null || right != null;
    } else {
      s.append("null");

      q.add(null);
      q.add(null);

      return false;
    }
  }

}

