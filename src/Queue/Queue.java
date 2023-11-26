package Queue;

/**
 * Queue ADT.
 *
 * @param <T> base type.
 */
public interface Queue<T> {

  /**
   * Adds a new element to the back of this queue.
   *
   * @param value to be added
   */
  void enqueue(T value);

  /**
   * Removes the element at the front of this queue.
   *
   * @throws EmptyException when empty() == true.
   */
  void dequeue() throws EmptyException;

  /**
   * Peeks at the front value without removing it.
   *
   * @return the value at the front of this queue.
   * @throws EmptyException when empty() == true.
   */
  T front() throws EmptyException;

  /**
   * Checks if empty.
   *
   * @return true if this queue is empty and false otherwise.
   */
  boolean empty();
}

