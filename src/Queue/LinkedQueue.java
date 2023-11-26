package Queue;

/*
first in, first out
front of the queue: head node
tail of the queue: tail node
thus enqueue: creates a new node, and update the tail node
dequeue: head = head.next
front: return head.data
//
 */

/**
 * Linked implementation of Queue ADT.
 *
 * @param <T> base type.
 */
public class LinkedQueue<T> implements Queue<T> {

  private Node<T> head;
  private Node<T> tail;
  private int numElements;

  private static class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  public LinkedQueue() {
    this.head = null;
    this.tail = null;
    this.numElements = 0;
  }

  @Override
  public void enqueue(T value) {
    if (empty()) {
      head = new Node<>(value);
      tail = head;
    } else {
      tail.next = new Node<>(value);
      tail = tail.next;
    }
    numElements++;
  }

  @Override
  public void dequeue() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    numElements--;
    head = head.next;
  }

  @Override
  public T front() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    return head.data;
  }

  @Override
  public boolean empty() {
    return numElements == 0;
  }
}
