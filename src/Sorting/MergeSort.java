package Sorting;

import java.util.Arrays;
// runtime: O(nlgn): division of sequence is O(lgn), merge is O(n) [k * O(n/k) = O(n)]
// can be optimized to O(n) when given sorted array

public class MergeSort {
  public static void main(String[] args) {
    int[] arr = {5, 1, 3, 0, 8, 2, 4, 9};
    System.out.println("Input: " + Arrays.toString(arr));
    mergeSort(arr);
    System.out.println("Output: " + Arrays.toString(arr));
  }

  /**
   * Sorts the input arr.
   * @param arr an array of integers.
   */
  public static void mergeSort(int[] arr) {
    mergeSort(arr, 0, arr.length);
  }

  // Pre: left and right are valid indices such that
  //        0 <= left <= right <= arr.length;
  // Post: arr[left] ... arr[right-1] is sorted.
  private static void mergeSort(int[] arr, int left, int right) {
    if (right - left < 2) {
      return; // base case: no need to sort when arr length is 0 or 1
    }

    int mid = (left + right) / 2;
    mergeSort(arr, left, mid); // sort left half: left...mid-1 is sorted
    mergeSort(arr, mid, right); // sort right half: mid ... right-1 is sorted
    // strategy to make runtime O(n) for a sorted array:
    // 因为如果arr[mid-1] <= arr[mid]，就没必要merge, 直接并在一起就可以
    // 对于一个sorted array，我们分到最后一层，有n个subarray，我们要把他合并到n/2, n/4, ..., 1个array
    // 对于n个subarray，我们需要做n/2次比较，发现每次都是arr[mid - 1] <= arr[mid], 所以escape merging
    // 然后获得了n/2个subarray, 继续做n/4次比较，发现同样的事，escape merging, 获得n/4个subarray
    // 所以总共：我们需要做n/2 + n/4 + ... + 1 次比较，sum to O(n) 所以runtime是O(n)
    if (arr[mid - 1] > arr[mid]) { // escape comparision
      merge(arr, left, mid, right);
    }
  }

  // Pre:
  //  1) left, mid and right are valid indices such that
  //        0 <= left <= mid < right <= arr.length;
  //  2) arr[left] ... arr[mid-1] is sorted
  //  3) arr[mid] ... arr[right-1] is sorted
  // Post: arr[left] ... arr[right-1] is sorted.
  private static void merge(int[] arr, int left, int mid, int right) {
    int i = left; // left half: starting point
    int j = mid; // right half: starting point
    int k = 0; // index for the new array

    int[] tmp = new int[right - left];

    // left half: left ... mid-1
    // right half: mid ... right-1
    while (i < mid && j < right) {
      if (arr[i] <= arr[j]) {
        tmp[k++] = arr[i++];
      } else {
        tmp[k++] = arr[j++];
      }
    }
    // 以上为数组前半段和后半段比较，选择小的先放进tmp
    // INVARIANT: tmp[0...k) is sorted

    // 当以上有一方为false, i.e. i < mid && j < right 中之一为false，说明一条数组已经全部被插入进了tmp，接下来只需要把
    // 另一条数组直接插进tmp就可以
    while (i < mid) {
      tmp[k++] = arr[i++];
    }
    while (j < right) {
      tmp[k++] = arr[j++];
    }

    // copy stuff over from tmp to arr
    // 可以：System.arraycopy(tmp, 0, arr, left, k);
    // 也可以:
    for (i = left, j = 0; i < right; i++, j++) {
      arr[i] = tmp[j];
    }
  }
}
