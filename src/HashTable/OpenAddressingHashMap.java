package HashTable;

import Map.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddressingHashMap<K, V> implements Map<K, V> {

  private int numElements;
  private int numFilledCells;
  private int capacity;
  private Node<K, V>[] hashTable;
  private final int[] primes = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597,
      3203, 6421, 12853, 25717, 51437,102877, 205759, 411527, 823117, 1646237,3292489, 6584983, 13169977};
  private int primeIdx;
  private final double loadFactor = 0.75;
  private final Node<K,V> tombstone = new Node<>();

  /**
   * Constructs an Open Addressing Hash Map with default initial capacity and settings.
   */
  public OpenAddressingHashMap() {
    numElements = 0;
    primeIdx = 1;
    capacity = primes[primeIdx];
    hashTable = new Node[capacity];
  }

  private int getIndex(K key) {
    return Math.abs(key.hashCode() % capacity); // return the hashCode for a given key
  }

  private void rehash() {
    primeIdx++;
    if (primeIdx < primes.length) {
      capacity = primes[primeIdx]; // choose the capacity to be the next prime number in the array
    } else {
      capacity = capacity * 2 + 1; // if the table is larger than the max held by the primes array
    }
    // reinitialize the hashTable
    numElements = 0;
    numFilledCells = 0;
    Node<K, V>[] temp = hashTable;
    hashTable = new Node[capacity];
    for (Node<K, V> n : temp) { // copy the oldTable to the new one
      if (n != null && n != tombstone) {
        insert(n.getKey(), n.getValue());
      }
    }
  }

  private int find(K key) {
    int index = getIndex(key);
    while (hashTable[index] != null || hashTable[index] == tombstone) { // if the given pos is taken
      if (!(hashTable[index] == tombstone) && hashTable[index].getKey().equals(key)) {
        return index; // ensure the returned index corresponds to a non-tombstone node
      }
      index++;
      index = index % capacity;
    }
    return -1;
  }


  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    int index = getIndex(k);
    while (hashTable[index] != null) { // if the given pos is taken
      if (!(hashTable[index] == tombstone) && hashTable[index].getKey().equals(k)) {
        throw new IllegalArgumentException("duplicate key " + k);
      }
      index++;
      index = index % capacity;
    }

    hashTable[index] = new Node<>(k, v);
    numElements++;
    numFilledCells++;

    if (loadFactor * capacity < numFilledCells) { // if numFilledCells/capacity > loadFactor, need rehash
      rehash();
    }
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    int index = find(k);
    if (index == -1) { // if we can't find the key
      throw new IllegalArgumentException("cannot find key " + k);
    }
    V value = hashTable[index].getValue();
    hashTable[index] = tombstone; // put a tombstone to the removed pos
    numElements--;
    return value;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    int index = find(k);
    if (index == -1) { // if we can't find the key
      throw new IllegalArgumentException("cannot find key " + k);
    }
    hashTable[index].setValue(v);
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    int index = find(k);
    if (index == -1) { // if we can't find the key
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return hashTable[index].getValue();
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return this.find(k) != -1;
  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Iterator<K> iterator() {
    return new OpenAddressingHashMapIterator();
  }


  private class OpenAddressingHashMapIterator implements Iterator<K> {
    private int currentIndex;

    OpenAddressingHashMapIterator() {
      this.currentIndex = 0;
      // find the first non-null and non-tombstone index
      while (currentIndex < capacity && (hashTable[currentIndex] == null || hashTable[currentIndex] == tombstone)) {
        currentIndex++;
      }
    }

    @Override
    public boolean hasNext() {
      return currentIndex < capacity;
    }

    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in the table");
      }

      K key = hashTable[currentIndex].getKey();
      currentIndex++;

      // Move the index to the next non-null and non-tombstone position
      while (currentIndex < capacity && (hashTable[currentIndex] == null || hashTable[currentIndex] == tombstone)) {
        currentIndex++;
      }

      return key;
    }
  }

  private static class Node<K, V> {
    K key;
    V value;

    Node() {
      key = null;
      value = null;
    }

    Node(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey() {
      return key;
    }

    public V getValue() {
      return value;
    }

    public void setValue(V value) {
      this.value = value;
    }
  }
}

