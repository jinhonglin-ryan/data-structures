package DoublyLinkedList;

/**
 * Generic position interface.
 * A client who receives a value of type Position have one operation at their disposal: get
 * which returns the data stored at that position. However, they will not directly access data
 * or next/prev reference variables.
 *
 * @param <T> the element type.
 */
public interface Position<T> {

  /**
   * Read element from this position.
   *
   * @return element at this position.
   */
  T get();
}