package Set;

// A set is an iterable collection of unique elements.
// A set has no particular ordering of elements (neither by position nor by value).

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArraySet<T> implements Set<T> {

  private T[] data;
  private int numElements;
  private int version;

  public ArraySet() {
    this.data = (T[]) new Object[1];
    this.numElements = 0;
    this.version = 0;
  }

  private int find (T t) {
    for (int i = 0; i < numElements; i++) {
      if (t.equals(data[i])) {
        return i; // found the element, return the index
      }
    }
    return -1; // element not founded
  }

  private boolean full() {
    return numElements >= data.length;
  }

  private void grow() {
    T[] big = (T[]) new Object[numElements * 2];
    System.arraycopy(data, 0, big, 0, numElements);
    data = big;
  }

  @Override
  public void insert(T t) {
    if (!has(t)) {
      data[numElements++] = t;
    }

    if (full()) {
      grow();
    }

    version++;
  }

  @Override
  public void remove(T t) {
    int i = find(t);
    if (i != -1) {
      data[i] = data[--numElements]; // put the last element to this pos
    }
    version++;
  }

  @Override
  public boolean has(T t) {
    return find(t) != -1;
  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Set<T> union(Set<T> other) {
    Set<T> unionSet = new ArraySet<T>();

    for (T num: this) {
      unionSet.insert(num);
    }

    if (other != this) {
      for (T num: other) {
        unionSet.insert(num);
      }
    }

    return unionSet;
  }

  @Override
  public Set<T> intersect(Set<T> other) {
    Set<T> intersectSet = new ArraySet<T>();

    for (T num: this) {
      if (this == other || other.has(num)) {
        intersectSet.insert(num);
      }
    }

    return intersectSet;
  }

  @Override
  public Set<T> subtract(Set<T> other) {
    Set<T> diff = new ArraySet<T>();

    for (T num : this) {
      if (this != other && !other.has(num)) {
        // we do this != other because otherwise we will get
        // ConcurrentModificationException when has is called! 因为has calls find, find performs transpose
        // 因为如果this == other, 那么other.has会导致this和other的version变化，
        // 再用this的iterator遍历checkVersion的时候会throw ConcurrentModificationException
        diff.insert(num);
      }
    }
    return diff;
  }

  @Override
  public Iterator<T> iterator() {
    return new SetIterator();
  }

  private class SetIterator implements Iterator<T> {
    private int curr;
    private final int version;

    public SetIterator() {
      this.curr = 0;
      this.version = ArraySet.this.version;
    }

    private void checkVersion() throws ConcurrentModificationException {
      if (version != ArraySet.this.version) {
        throw new ConcurrentModificationException();
      }
    }

    @Override
    public boolean hasNext() {
      checkVersion();
      return curr < numElements;
    }

    @Override
    public T next() throws NoSuchElementException {
      checkVersion();
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return data[curr++];
    }
  }
}
