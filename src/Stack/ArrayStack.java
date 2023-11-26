package Stack;

/*
Last in, first out
top of the ArrayStack: the end of the array
thus, push adds the element to the end of the array
top returns the element at the end of the array
pop deletes the element at the end of the array
 */

/**
 * Stack ADT implemented using an array.
 *
 * @param <T> base type.
 */
public class ArrayStack<T> implements Stack<T> {
  private T[] data;
  private int capacity;
  private int numElements;

  /**
   * Construct an ArrayStack.
   */
  public ArrayStack() {
    capacity = 10;
    data = (T[]) new Object[capacity];
    numElements = 0;
  }

  @Override
  public boolean empty() {
    return numElements == 0;
  }

  @Override
  public T top() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    return data[numElements - 1];
  }

  @Override
  public void pop() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    numElements--;
  }

  // Exercise: if we have capacity n and push n+1 times
  // For the first n-1 pushes, runtime complexity for push is O(1)
  // For the nth push, we call grow() in push(), so runtime is O(n)
  // Then for the n+1 push and the following pushes, runtime goes back to O(1)

  // Exercise: if we have a capacity 1 and call push n times, grow() doubles the capacity
  // what is the amortized costs over n pushes?
  // the first push is going to put one element in the array and grow the array
  // the second push is going to put one more element in the array and grow the array
  // * we are now counting the amount of work we do
  // basically after each grow, we need to copy the previous elements and push more elements to reach the capacity
  // when we have capacity 1, we push 1 element in it, that's 1 unit of work
  // when we have capacity 2, we copy 1 element from before and call push again to reach the capacity, that's 2 unit of work
  // when we have capacity 4, we copy previous 2 elements and do 2 more pushes, that's 4
  // when we have capacity 8, we copy previous 4 elements and do 4 more pushes, that's 8
  // ...
  // when we have capacity n, we copy the n/2 elements and do n/2 more pushes, that's n
  // in total, when we have n pushes, which means we have reached the capacity n, how many work we have done so far?
  // that's 1 + 2 + 4 + 8 + 16 + ... + n, sum them up we have 2^(lgn+1)-1, simplify that's 2n-1, O(n)
  // so the amortized costs over n pushes is O(n)/n = O(1)

  // Exercise: if we have capacity 1 and call push n times, grow() increases the capacity by 1 each time
  // what is the amortized costs over n pushes?
  // we are also counting the amount of work we do
  // basically after each grow, we need to copy the previous elements and push more elements to reach the capacity
  // when we have capacity 1, we push 1 element in it, that's 1 unit of work
  // when we have capacity 2, we copy 1 element from before and call push again to reach the capacity, that's 2 unit of work
  // when we have capacity 3, we copy previous 2 elements and do 1 more pushes, that's 3
  // when we have capacity 4, we copy previous 3 elements and do 1 more pushes, that's 4
  // in total, that's 1 + 2 + 3 + ... + n, so (n+1)*n/2 = O(n^2)
  // the amortized costs is just O(n^2) / n = O(n)
  @Override
  public void push(T value) {
    data[numElements++] = value;
    if (numElements == capacity) {
      grow();
    }
  }

  // runtime and auxiliary space are O(n) where n is the # of elements
  // as we have to new a space of n elements
  // and copy n elements to the new array
  private void grow() {
    capacity *= 2;
    T[] newData = (T[]) new Object[capacity];
    for (int i = 0; i < numElements; i++) {
      newData[i] = data[i];
    }
    data = newData;
  }
}
