package HashTable;


import Map.Map;
import java.util.Iterator;

/**
 * A wrapper class around Java's HashMap that conforms to the Map interface.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class JdkHashMap<K, V> implements Map<K, V> {

  private java.util.Map<K, V> map;

  /**
   * Instantiate JdkHashMap.
   */
  public JdkHashMap() {
    map = new java.util.HashMap<>();
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null || map.containsKey(k)) {
      throw new IllegalArgumentException();
    }
    map.put(k, v);
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null || !map.containsKey(k)) {
      throw new IllegalArgumentException();
    }

    return map.remove(k);
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k == null || !map.containsKey(k)) {
      throw new IllegalArgumentException();
    }
    map.put(k, v);
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null || !map.containsKey(k)) {
      throw new IllegalArgumentException();
    }
    return map.get(k);
  }

  @Override
  public boolean has(K k) {
    return map.containsKey(k);
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public Iterator<K> iterator() {
    return map.keySet().iterator();
  }
}