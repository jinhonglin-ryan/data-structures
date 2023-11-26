package Queue;

/*
first in, first out
two vars: front and back
enqueue: add to the back
front: return the element with the index front
dequeue: simply increments front
 */

/**
 * Array Implementation of the Queue ADT.
 *
 * @param <T> base type.
 */
public class ArrayQueue<T> implements Queue<T> {
  private int front;
  private int back;
  private int numElements;
  private int capacity;
  private T[] data;

  public ArrayQueue() {
    front = 0;
    back = 0;
    numElements = 0;
    capacity = 10;
    data = (T[]) new Object[capacity];
  }

  @Override
  public void enqueue(T value) {
    data[back] = value;
    back++;
    back %= capacity;
    numElements++;
  }

  @Override
  public void dequeue() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    front++;
    front %= capacity;
    // advanced: front = ++front % capacity
    numElements--;
  }

  @Override
  public T front() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    return data[front];
  }

  @Override
  public boolean empty() {
    return numElements == 0;
  }
}
