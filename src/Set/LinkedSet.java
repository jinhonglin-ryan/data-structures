package Set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
Set implemented using a doubly linked list, no sentinels

we added an example for a "fail-fast" iterator

To make an iterator "fail-fast" we need to be able to tell that the data
structure has been modified since the iteration started. We use a version
number in the LinkedSet class to achieve this.

The number starts at 0 and is incremented whenever a Set operation modifies
the internal list. Each iterator also "remembers" the version number it was created for.
 We can then check for modifications by comparing version numbers in the Iterator
 operations: If we notice a mismatch, we raise an exception.
 */
public class LinkedSet<T> implements Set<T> {

  private static class Node<T> {
    T data;
    Node<T> next;
    Node<T> prev;

    Node(T data) {
      this.data = data;
    }
  }

  // see instructor-LinkedSet for the LinkedSet that uses sentinel:
  // head.data = null;
  // front = head.next;
  private Node<T> head;
  private int numElements;
  private int version;

  private Node<T> find(T t) {
    // see instructor-LinkedSet for the move-to-front find()
    // remove the founded node and prepend it - heuristic
    // It speeds up linear search in the linked list if the target item
    // is likely to be searched again soon.

    Node<T> curr = head;
    while (curr != null) {
      if (t.equals(curr.data)) {
        return curr;
      }
      curr = curr.next;
    }
    return null;
  }

  private void prepend(T t) {
    if (head == null) {
      head = new Node<>(t);
    } else {
      Node<T> newNode = new Node<>(t);
      newNode.next = head;
      head.prev = newNode;
      head = newNode;
    }
  }

  @Override
  public void insert(T t) {
    if (has(t)) {
      return; // only allow one occurrence of an element
    }
    prepend(t);
    numElements++;
    version++;
  }

  @Override
  public void remove(T t) {
    Node<T> target = find(t);

    if (target == null) {
      return; // no such element exists in the set
    }

    remove(target); // call helper function
    version++;
    numElements--;
  }

  private void remove(Node<T> target) {
    if (target == head) {
      head = target.next;
    }

    Node<T> beforeTarget = target.prev;
    Node<T> afterTarget = target.next;

    if (beforeTarget != null) {
      beforeTarget.next = afterTarget;
    }

    if (afterTarget != null) {
      afterTarget.prev = beforeTarget;
    }
  }

  @Override
  public boolean has(T t) {
    return find(t) != null;
  }

  @Override
  public int size() {
    return numElements;
  }


  @Override
  public Set<T> union(Set<T> other) {
    Set<T> unionSet = new LinkedSet<T>();

    for (T data: this) {
      unionSet.insert(data);
    }
    // if this LinkedSet is not identical to other LinkedSet, we do union
    // Note that the repeated element is handled in insert()
    if (this != other) {
      for (T data: other) {
        unionSet.insert(data);
      }
    }

    return unionSet;
  }

  @Override
  public Set<T> intersect(Set<T> other) {
    Set<T> intersectSet = new LinkedSet<T>();

    for (T data : this) {
      if (this == other || other.has(data)) {
        intersectSet.insert(data);
      }
    }

    return intersectSet;
  }

  @Override
  public Set<T> subtract(Set<T> other) {
    Set<T> difference = new LinkedSet<T>();

    for (T data : this) {
      if (this != other && !other.has(data)) {
        // we do this != other because otherwise we will get
        // ConcurrentModificationException when has is called! 因为has calls find, find performs move-to-front
        // 因为如果this == other, 那么other.has会导致this和other的version变化，
        // 再用this的iterator遍历checkVersion的时候会throw ConcurrentModificationException
        difference.insert(data);
      }
    }
    return difference;
  }


  @Override
  public Iterator<T> iterator() {
    return new SetIterator();
  }

  private class SetIterator implements Iterator<T> {
    private Node<T> curr;
    private final int version;

    /*
    version的用法：for fail-fast iterator
    1. 每个LinkedSet都有一个version, 对于LinkedSet的modification（insert, remove）都会改变version
    2. 假设这个LinkedSet已经完成，有一个version，然后我们开始iterate over the set
    3. iterator也有一个version，并且这个version是不可被改变的，与当前LinkedSet的version一样
    4. 当我们开始iteration的时候，我们不希望set再被改变，希望set能保持不变 直到iteration结束
    5. 所以我们需要checkVersion方法，来保证比如在多线程的情况下，多线程的操作不改变set直到iteration结束
    如果没有version，会怎么样？
    1. 假设我们没有version，然后我们开始iterate ArraySet 然后用以上定义的remove方法改变了set
    2. 以上的remove：把最后一个element换到要remove element的位置
    3. 假设我们已经经过了要被remove的位置，然后在另一个线程里执行remove，numElements--
    4. 这样再iterate的时候，因为numElements--,最后一个element就不会被visit到
    5. 即便最后一个element 被放到了前面的remove位置（由于我们iterate过了那个位置，不会回来）
    version的执行：
    1. 任何对于set structural modification要改变version（version++） 比如insert和remove
    2. 每次执行iteration方法的时候，hasNext和next都要check当前iterator的version和set version是不是一样
    3. 如果不一样，说明set被改变了，我们马上throw ConcurrentModificationException
    */
    SetIterator() {
      curr = LinkedSet.this.head;
      version = LinkedSet.this.version;
    }

    private void checkVersion() throws ConcurrentModificationException {
      if (version != LinkedSet.this.version) {
        throw new ConcurrentModificationException();
      }
    }


    @Override
    public boolean hasNext() {
      checkVersion();
      return curr != null;
    }

    @Override
    public T next() throws NoSuchElementException {
      checkVersion();
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T t = curr.data;
      curr = curr.next;
      return t;
    }
  }


}
