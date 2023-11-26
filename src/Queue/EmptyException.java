package Queue;

/**
 * Exception to signal an operation was invoked
 * on an empty data structures.
 */
public class EmptyException extends RuntimeException {

  /**
   * Constructs a new EmptyException.
   */
  public EmptyException() {
  }

  /**
   * Constructs a new EmptyException with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the getMessage() method.
   */
  public EmptyException(String message) {
    super(message);
  }
}