package Sorting;

import java.util.Arrays;

public class QuickSort_Clean {
  // AcWing QuickSort: Take the first element to be the pivot point
  public static void quickSort(int[] arr, int l, int r) {
    if (l >= r) {
      return;
    }

    int p = arr[l];
    int i = l - 1;
    int j = r + 1;

    while (i < j) {
      do {
        i++;
      } while (arr[i] < p);
      do {
        j--;
      } while (arr[j] > p);

      if (i < j) {
        swap(arr, i, j);
      }
    }

    quickSort(arr, l, j);
    quickSort(arr, j + 1, r);
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  public static void main(String[] args) {
    int[] arr = {20, 13, 7, 71, 31, 10, 5, 50, 100};
    System.out.println("Input: " + Arrays.toString(arr));
    quickSort(arr, 0, arr.length - 1);
    System.out.println("Output: " + Arrays.toString(arr));
  }
}
