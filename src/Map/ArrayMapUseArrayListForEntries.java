package Map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Map implemented using an unsorted ArrayList internally.
 *
 * <p>This implementation is deliberately inefficient so all
 * operations take O(n) time. The iterator() method makes a copy of
 * all keys, so it takes O(n) as well. The only positive thing here
 * is its simplicity.</p>
 *
 * <p>Note that we could do slightly better (in several ways!) by using
 * two arrays in parallel and managing our own insert/remove. You
 * should probably think about this because it makes for a great exam
 * question... :-)</p>
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class ArrayMapUseArrayListForEntries<K, V> implements Map<K, V> {
  private List<Entry<K,V>> data;
  // private Entry<K, V>[] data;

  // private List<K> keys;
  // private List<V> values;

  /**
   * Create an empty map.
   */
  public ArrayMapUseArrayListForEntries() {
    this.data = new ArrayList<>();
  }

  // Find entry for key k, throw exception if k is null.
  private Entry<K,V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }

    for (Entry<K,V> e : this.data) { // List is iterable
      if (k.equals(e.key)) {
        return e;
      }
    }
    return null;
  }

  // Find entry for key k, throw exception if k not mapped.
  private Entry<K,V> findForSure(K k) {
    Entry<K,V> e = this.find(k);
    if (e == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return e;
  }

  @Override
  public void insert(K k, V v) {
    Entry<K,V> e = this.find(k);
    if (e != null) {
      throw new IllegalArgumentException("duplicate key " + k);
    }
    e = new Entry<K,V>(k, v);
    this.data.add(e);
  }

  @Override
  public V remove(K k) {
    Entry<K,V> e = this.findForSure(k);
    V v = e.value;
    this.data.remove(e);
    return v;
  }

  @Override
  public void put(K k, V v) {
    Entry<K,V> e = this.findForSure(k);
    e.value = v;
  }

  @Override
  public V get(K k) {
    Entry<K,V> e = this.findForSure(k);
    return e.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return this.find(k) != null;
  }

  @Override
  public int size() {
    return this.data.size();
  }

  @Override
  public Iterator<K> iterator() {
    List<K> keys = new ArrayList<>();
    for (Entry<K,V> e : this.data) {
      keys.add(e.key);
    }
    return keys.iterator();
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("{");
    for (int i = 0; i < this.data.size(); i++) {
      Entry<K,V> e = this.data.get(i);
      s.append("" + e.key + ": " + e.value);
      if (i < this.data.size() - 1) {
        s.append(", ");
      }
    }
    s.append("}");
    return s.toString();
  }

  // Entry to store a key, and a value pair.
  private static class Entry<K,V> {
    K key;
    V value;

    Entry(K k, V v) {
      this.key = k;
      this.value = v;
    }
  }
}
