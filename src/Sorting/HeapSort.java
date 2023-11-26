package Sorting;

public class HeapSort {
  private static Integer[] sample1() {
    return new Integer[]{2,12,3,23,1,23,123,12,3,4,89};
  }

  public static void main(String[] args) {
    Integer[] data = sample1();
    // 让数组从1开始编号，这样方便access 左孩子右孩子
    // 数组从1开始编号：index为i的节点，左孩子是2i，右孩子是2i+1
    Integer[] new_data = indexShift(data);

    System.out.println("Before:");
    for (int i = 1; i < new_data.length; i++) {
      System.out.print(new_data[i] + " ");
    }
    System.out.println();

    heapSort(new_data);

    System.out.println("After:");
    for (int i = 1; i < new_data.length; i++) {
      System.out.print(new_data[i] + " ");
    }
    System.out.println();
  }


  // 最后的数组 从小到大排列
  private static void heapSort(Integer[] data) {
    int numNodes = data.length - 1; // index shift后多了一个idx为0的null
    // 先建堆，然后依次输出root，输出root后删除root，调整堆结构，再输出root

    // 1. 建堆
    // 从numNodes/2 开始建，这是heap倒数第二层最右边的node，是第一个有children, 需要调整结构的node
    // 一直建到第一个node
    for (int i = numNodes / 2; i >= 1; i--) {
      sink(data, i, numNodes); //对于每个有children的node，调整heap结构，保持max-heap的结构
    }

    // 2. 排序
    // 把数组里最后一个数与第一个数(root)交换，因为我们的heap是max_heap，第一个数永远是最大
    for (int i = numNodes; i >= 1; i--) {
      swap(data, 1, numNodes);
      numNodes--; // 屏蔽掉刚刚换过去的最大的数
      sink(data, 1, numNodes); // 再维护刚换过去数组里最后的数，保证heap structure
    }
  }


  // index: 待维护节点的下标
  // numNodes: 数组总长度
  // data: 数组
  // percolate down data[index] to restore heap order
  // the percolation is bounded to go down no further than data[numNodes]
  private static void sink(Integer[] data, int index, int numNodes) {
    int largest_idx = index;
    int rson_idx = index * 2 + 1;
    int lson_idx = index * 2;

    // find the largest index:
    if (rson_idx <= numNodes && data[rson_idx] > data[largest_idx]) {
      largest_idx = rson_idx;
    }
    if (lson_idx <= numNodes && data[lson_idx] > data[largest_idx]) {
      largest_idx = lson_idx;
    }

    // now we have the largest index
    // if the largest index is not the index itself, we need to swap
    // after swap, 原先index的地方被放了largest_idx对应的值，没问题
    // 原先largest_index的地方放了index对应的值，但这个节点可能还需要维护，因此再call一遍sink
    if (largest_idx != index) {
      swap(data, largest_idx, index);
      sink(data, largest_idx, numNodes);
    }
  }

  private static void swap(Integer[] data, int i, int j) {
    int temp = data[i];
    data[i] = data[j];
    data[j] = temp;
  }
  private static Integer[] indexShift(Integer[] data) {
    Integer[] new_data = new Integer[data.length + 1];
    System.arraycopy(data, 0, new_data, 1, data.length);
    return new_data;
  }
}


