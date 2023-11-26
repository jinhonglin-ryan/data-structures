package Stack;

/*
Last in, first out
top of the LinkedStack: the head of the LinkedList
thus, push prepends the LinkedList
top returns the head of the LinkedList
pop deletes the current head
*/
/**
 * Stack ADT implemented using linked nodes.
 *
 * @param <T> base type.
 */
public class LinkedStack<T> implements Stack<T> {

  private Node<T> head;

  /**
   * Construct an ListStack.
   */
  public LinkedStack() {
    head = null;
  }

  @Override
  public boolean empty() {
    return head == null;
  }
  @Override
  public T top() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    return head.data;
  }

  @Override
  public void pop() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    head = head.next;
  }

  @Override
  public void push(T t) {
    Node<T> newNode = new Node<>();
    newNode.data = t;
    newNode.next = head;
    head = newNode;
  }

  private static class Node<T> {
    T data;
    Node<T> next;
  }
}

