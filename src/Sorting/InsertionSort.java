package Sorting;

public class InsertionSort {
  public static void insertionSort(int[] arr) {
    // 特征：分为已排序区间和未排序区间
    // 把未排序区间的值插入到已排序区间的合适位置
    // 默认第一位是排好的，从第二位开始一直到数组结尾

    // worst-case runtime O(n^2)
    // best-case runtime O(n)
    // space O(n): input O(n), auxiliary O(1)
    for (int i = 1; i < arr.length; i++) {
      int j = i;
      // 只要当前位置比前一位置小，就交换
      // 然后将j--，再与前面比较，直到不再比前一位小了
      while (j > 0 && arr[j] < arr[j - 1]) {
        int temp = arr[j];
        arr[j] = arr[j - 1];
        arr[j - 1] = temp;
        j--;
      }
    }
  }

  public static void insertionSortRecursive(int[] arr, int n) {
    if (n <= 1) {
      return;
    }

    insertionSortRecursive(arr, n - 1);

    int key = arr[n - 1];
    int j = n - 2;
    while (j >= 0 && arr[j] > key) {
      arr[j + 1] = arr[j];
      j--;
    }
    arr[j + 1] = key;
  }

  public static void main(String[] args) {
    int[] arr = {12, 11, 13, 5, 6};
    insertionSortRecursive(arr, arr.length);
    System.out.println("Sorted array:");
    for (int value : arr) {
      System.out.print(value + " ");
    }
  }
}
