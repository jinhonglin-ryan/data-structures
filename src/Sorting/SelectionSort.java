package Sorting;

public class SelectionSort {
  public static void selectionSort(int[] arr) {
    // 特征：分为已排序区间和未排序区间
    // 选取最小值 与当前位置交换
    // 最外层只需要遍历到length - 2 因为当所有最小值都被放在最前面，最后一个位置一定是最大值
    // 里层是从i + 1到length - 1

    // worst-case runtime O(n^2)
    // best-case runtime O(n^2)
    // space O(n): input O(n), auxiliary O(1)

    for (int i = 0; i < arr.length - 1; i++) {
      int min = i;

      // 找到最小值的index
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] < arr[min]) {
          min = j;
        }
      }

      if (min != i) { // swap i 和最小值
        int temp = arr[i];
        arr[i] = arr[min];
        arr[min] = temp;
      }
    }
  }

  public static void selectionSortRecursive(int[] arr, int n, int index) {
    if (index == n) {
      return;
    }

    int minIndex = index;
    for (int i = index + 1; i < n; i++) {
      if (arr[i] < arr[minIndex]) {
        minIndex = i;
      }
    }

    int temp = arr[index];
    arr[index] = arr[minIndex];
    arr[minIndex] = temp;

    selectionSortRecursive(arr, n, index + 1);
  }

  public static void main(String[] args) {
    int[] arr = {12, 11, 13, 5, 6};
    selectionSortRecursive(arr, arr.length, 0);
    System.out.println("Sorted array:");
    for (int value : arr) {
      System.out.print(value + " ");
    }
  }
}
