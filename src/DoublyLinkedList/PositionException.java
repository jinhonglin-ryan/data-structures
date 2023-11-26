package DoublyLinkedList;

/**
 * Exception for position-based data structures.
 *
 * <p>Data structures that use Position interface throw PositionException
 * if the position provided to them is null or otherwise invalid.</p>
 */
public class PositionException extends RuntimeException {
  /**
   * Constructs a new PositionException.
   */
  public PositionException() {

  }

  /**
   * Constructs a new PositionException with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the getMessage() method.
   */
  public PositionException(String message) {
    super(message);
  }
}