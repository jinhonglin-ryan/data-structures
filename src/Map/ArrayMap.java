package Map;

import java.util.Iterator;

public class ArrayMap<K,V> implements Map<K,V> {
  private K[] keys;
  private V[] values;
  private int numElements;

  public ArrayMap(int capacity) {
    this.keys = (K[]) new Object[capacity];
    this.values = (V[]) new Object[capacity];
    this.numElements = 0;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("can't handle null key");
    }

    if (numElements == keys.length) { // map is full
      throw new IllegalArgumentException("full");
    }

    for (int i = 0; i < keys.length; i++) {
      if (keys[i].equals(k)) {
        throw new IllegalArgumentException("duplicate keys");
      }
    }

    keys[numElements] = k;
    values[numElements] = v;
    numElements++;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("can't handle null key");
    }

    int index = -1;
    for (int i = 0; i < keys.length; i++) {
      if (keys[i].equals(k)) {
        index = i;
        break;
      }
    }

    if (index == -1) {
      throw new IllegalArgumentException("can't find the key");
    }

    V v = values[index];
    // move the last element to the removed position to maintain order
    keys[index] = keys[numElements - 1];
    values[index] = values[numElements - 1];
    numElements--;
    return v;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {

  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    return null;
  }

  @Override
  public boolean has(K k) {
    return false;
  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Iterator<K> iterator() {
    return null;
  }
}
