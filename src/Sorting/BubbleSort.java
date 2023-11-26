package Sorting;

public class BubbleSort {
  public static void bubbleSort(int[] arr) {
    // 特征：每次最外层循环，把最大的数放到数组末尾
    // 每次都从数组开始比较相邻的元素，如果第一个比第二个大，就交换

    // worst-case runtime O(n^2)
    // best-case runtime O(n)
    // space O(n): input O(n), auxiliary O(1)
    for (int i = 0; i < arr.length; i++) {
      boolean swap = false; //提前退出的标志

      for (int j = 0; j < arr.length - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
          swap = true; //表示有数据交换
        }
      }

      if (!swap) {
        break;  // 如果一整个排下来没有数据交换，说明排好了
      }
    }
  }

  public static void bubbleSortRecursive(int[] arr, int n) {
    if (n == 1) {
      return;
    }

    for (int i = 0; i < n - 1; i++) {
      if (arr[i] > arr[i + 1]) {
        int temp = arr[i];
        arr[i] = arr[i + 1];
        arr[i + 1] = temp;
      }
    }

    bubbleSortRecursive(arr, n - 1);
  }

  public static void main(String[] args) {
    int[] arr = {12, 11, 13, 5, 6};
//    sort(arr);
    bubbleSortRecursive(arr, arr.length);
    System.out.println("Sorted array:");
    for (int value : arr) {
      System.out.print(value + " ");
    }
  }
}
