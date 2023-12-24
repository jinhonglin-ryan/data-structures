package Map;

/**
 * Map ADT.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public interface Map<K, V> extends Iterable<K> {
  /**
   * Insert a new key/value pair.
   *
   * @param k The key.
   * @param v The value to be associated with k.
   * @throws IllegalArgumentException If k is null or already mapped.
   */
  void insert(K k, V v) throws IllegalArgumentException;

  /**
   * Remove an existing key/value pair.
   *
   * @param k The key.
   * @return The value that was associated with k.
   * @throws IllegalArgumentException If k is null or not mapped.
   */
  V remove(K k) throws IllegalArgumentException;

  /**
   * Update the value associated with a key.
   *
   * @param k The key.
   * @param v The value to be associated with k.
   * @throws IllegalArgumentException If k is null or not mapped.
   */
  void put(K k, V v) throws IllegalArgumentException;

  /**
   * Get the value associated with a key.
   *
   * @param k The key.
   * @return The value associated with k.
   * @throws IllegalArgumentException If k is null or not mapped.
   */
  V get(K k) throws IllegalArgumentException;

  /**
   * Check existence of a key.
   *
   * @param k The key.
   * @return True if k is mapped, false otherwise (even for null!).
   */
  boolean has(K k);

  /**
   * Number of mappings.
   *
   * @return Number of key/value pairs in the map.
   */
  int size();
}
