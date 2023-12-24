package HashTable;

import Map.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChainingHashMap<K, V> implements Map<K, V> {

  private int numElements;
  private int capacity;
  private Node<K, V>[] hashTable;
  private final int[] primes = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597,
      3203, 6421, 12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977};
  private int primeIdx;
  private final double loadFactor = 0.75;


  /**
   * Constructs a Chaining Hash Map with default initial capacity and settings.
   */
  public ChainingHashMap() {
    numElements = 0;
    primeIdx = 1;
    capacity = primes[primeIdx];
    hashTable = new Node[capacity];
  }

  private int getIndex(K key) {
    return Math.abs(key.hashCode() % capacity); // return the hashCode for a given key
  }

  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    int index = getIndex(k);
    if (hashTable[index] != null) { // this position is taken
      Node<K, V> temp = hashTable[index];
      while (temp != null) { // traverse the auxiliary data structure
        if (temp.key.equals(k)) {
          return temp; // means duplicate keys exist in the auxiliary data structure
        }
        temp = temp.next;
      }
    }
    // hashTable[index] == null means there is no such node with key k
    return null;
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
    Node<K, V>[] temp = hashTable;
    hashTable = new Node[capacity];
    for (Node<K, V> n : temp) { // copy the auxiliary data structure to the new hashTable
      Node<K, V> curr = n;
      while (curr != null) {
        insert(curr.key, curr.value);
        curr = curr.next;
      }
    }
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    if (find(k) != null) {
      throw new IllegalArgumentException("cannot have duplicate keys " + k);
    }
    int index = getIndex(k);
    Node<K, V> newNode = new Node<>(k, v);
    // prepend it to the current hashTable[index]
    newNode.next = hashTable[index];
    hashTable[index] = newNode;
    numElements++;
    if (loadFactor * capacity < numElements) { // if numElements/capacity > loadFactor, need rehash
      rehash();
    }

  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> node = find(k);
    if (node == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    int index = getIndex(k);
    V value = node.value;
    Node<K, V> prevNode = findPrevNode(k);
    if (prevNode == null) {
      hashTable[index] = hashTable[index].next; // make the hashTable[index] to null, we are removing the head
    } else {
      prevNode.next = node.next;
    }
    numElements--;
    return value;
  }

  private Node<K, V> findPrevNode(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    int index = getIndex(k);
    if (hashTable[index] != null) {
      Node<K, V> temp = hashTable[index];
      if (temp.key.equals(k)) {
        return null; // head of a linkedList has no prev node
      }
      while (temp.next != null) {
        if (temp.next.key.equals(k)) {
          return temp;
        }
        temp = temp.next;
      }
    }
    throw new IllegalArgumentException("cannot find key " + k);
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> node = find(k);
    if (node == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    node.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> node = find(k);
    if (node == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return node.value;
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
    return numElements;
  }

  @Override
  public Iterator<K> iterator() {
    return new ChainingHashMapIterator();
  }

  private class ChainingHashMapIterator implements Iterator<K> {
    private Node<K, V> curr;
    private int count; // counts the number of elements we have been iterated
    private int index;

    ChainingHashMapIterator() {
      count = 0;
      index = 0;
      curr = hashTable[index];
    }

    @Override
    public boolean hasNext() {
      return count < numElements; // count must be less than numElements
    }

    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in the table");
      }
      // if curr is null, either we are at the end of the auxiliary data structure, or the index is not taken
      while (curr == null) {
        index++; // move the index forward
        curr = hashTable[index];
      }
      K key = curr.getKey();
      count++;
      curr = curr.next; // move the curr to the next
      return key;
    }
  }

  private static class Node<K, V> {
    K key;
    V value;
    Node<K, V> next;

    Node() {
      key = null;
      value = null;
      next = null;
    }

    Node(K key, V value) {
      this.key = key;
      this.value = value;
      next = null;
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

