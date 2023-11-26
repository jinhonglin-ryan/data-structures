package Sorting;

import java.util.Arrays;

public class QuickSort {

  public static void main(String[] args) {
    int[] arr = {20, 13, 7, 71, 31, 10, 5, 50, 100};
    System.out.println("Input: " + Arrays.toString(arr));
    quicksort(arr);
    System.out.println("Output: " + Arrays.toString(arr));
  }

  /**
   * Sorts the input array. Take the last element to be the pivot
   *
   * @param arr an array of integers.
   */
  public static void quicksort(int[] arr) {
    quicksort(arr, 0, arr.length);
  }

  // Pre: 0 <= left <= right <= arr.length.
  // Post: arr[left] ... arr[right-1] is sorted.
  private static void quicksort(int[] arr, int left, int right) {
    // Base case: 0 or 1 elements
    if (right - left <= 1) {
      return;
    }

    // partition the array
    int partitionIndex = partition(arr, left, right);

    // Recursively sort the left and right partitions.
    quicksort(arr, left, partitionIndex);
    quicksort(arr, partitionIndex + 1, right);
  }

  // Partition by taking the right-most element as pivot.
  // Pre: 0 <= left <= right <= arr.length;
  // Post: a[left] <= .. <= pivot <= .. <= a[right - 1]
  // Returns: index of pivot element in array.
  private static int partition(int[] arr, int left, int right) {
    int p = right - 1; // point to pivot
    int l = left; // left marker
    int r = p - 1; // right marker

    while (l <= r) {
      while (l <= r && arr[l] <= arr[p]) {
        l++; // move l to right as long as condition holds.
      }

      while (l <= r && arr[r] >= arr[p]) {
        r--; // move r to left as long as condition holds.
      }

      if (l < r) {
        // arr[l] and arr[r] are out of order with respect to pivot
        swap(arr, l, r);
      }
    }

    if (l != p) {
      // move the pivot to its final place
      swap(arr, l, p);
    }
    return l;
  }

  // Swap arr[i] with arr[j]
  private static void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  // Here is a more concise implementation of partition
  //    int pivot = arr[right - 1];
  //    int l = left;
  //    int r = right - 2;
  //    while (l <= r) {
  //      if (arr[l] <= pivot) {
  //        l++;
  //      } else if (arr[r] > pivot) {
  //        r--;
  //      } else {
  //        swap(arr, l, r);
  //        l++;
  //        r--;
  //      }
  //    }
  //    swap(arr, l, right - 1);
  //    return l;

}
