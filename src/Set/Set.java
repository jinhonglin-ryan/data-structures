package Set;

/**
 * Sets of arbitrary values (not necessarily Comparable).
 * Iteration order is undefined.
 *
 * @param <T> Element type.
 */
public interface Set<T> extends Iterable<T> {
  // extends Iterable: set有iterator()方法
  /**
   * Insert a value.
   * Set doesn't change if we try to insert an existing value.
   * Post: has(t) == true.
   *
   * @param t Value to insert.
   */
  void insert(T t);

  /**
   * Remove a value.
   * Set doesn't change if we try to remove a non-existent value.
   * Post: has(t) == false.
   *
   * @param t Value to remove.
   */
  void remove(T t);

  /**
   * Test membership of a value.
   *
   * @param t Value to test.
   * @return True if t is in the set, false otherwise.
   */
  boolean has(T t);

  /**
   * Number of values.
   *
   * @return Number of values in the set, always greater equal to 0.
   */
  int size();
  /**
   * Constructing a new set with elements that are in this set or
   * in the other set.
   *
   * @param other set.
   * @return all elements that are in this set or the other set.
   */
  Set<T> union(Set<T> other);

  /**
   * Constructing a new set with elements that are in this set and
   * in the other set.
   *
   * @param other set.
   * @return the elements this set and other set have in common.
   */
  Set<T> intersect(Set<T> other);

  /**
   * Constructing a new set with elements that are in this set but not
   * in the other set.
   *
   * @param other set.
   * @return the elements in this set but not in the other set.
   */
  Set<T> subtract(Set<T> other);
}