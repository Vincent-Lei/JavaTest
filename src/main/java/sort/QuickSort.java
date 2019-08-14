package sort;

public class QuickSort {
    /**
     * 快速排序的基本思想
     *     　设当前待排序的无序区为R[low..high]，利用分治法可将快速排序的基本思想描述为：
     * ①分解：
     *     　在R[low..high]中任选一个记录作为基准(Pivot)，以此基准将当前无序区划分为左、右两个较小的子区间R[low..pivotpos-1)和R[pivotpos+1..high]，
     *     并使左边子区间中所有记录的关键字均小于等于基准记录(不妨记为pivot)的关键字pivot.key，右边的子区间中所有记录的关键字均大于等于pivot.key，
     *     而基准记录pivot则位于正确的位置(pivotpos)上，它无须参加后续的排序。
     *   注意：
     *     　划分的关键是要求出基准记录所在的位置pivotpos。划分的结果可以简单地表示为(注意pivot=R[pivotpos])：
     *     　R[low..pivotpos-1].keys≤R[pivotpos].key≤R[pivotpos+1..high].keys
     *                   其中low≤pivotpos≤high。
     * ②求解：
     *    　 通过递归调用快速排序对左、右子区间R[low..pivotpos-1]和R[pivotpos+1..high]快速排序。
     *
     * ③组合：
     *     　因为当"求解"步骤中的两个递归调用结束时，其左、右两个子区间已有序。对快速排序而言，"组合"步骤无须做什么，可看作是空操作。
     */
    private void quickSort(int[] sourceArray, int left, int right) {
        if (left >= right)
            return;
        int temp = sourceArray[left];
        int leftIndex = left;
        int rightIndex = right;
        while (leftIndex != rightIndex) {
            while (rightIndex > leftIndex && sourceArray[rightIndex] >= temp)
                rightIndex--;
            if (rightIndex > leftIndex)
                sourceArray[leftIndex] = sourceArray[rightIndex];
            while (rightIndex > leftIndex && sourceArray[leftIndex] <= temp)
                leftIndex++;
            if (rightIndex > leftIndex)
                sourceArray[rightIndex] = sourceArray[leftIndex];
        }
        sourceArray[leftIndex] = temp;
        quickSort(sourceArray, left, leftIndex - 1);
        quickSort(sourceArray, leftIndex + 1, right);
    }

    public void quickSort() {
        int length = 19;
        int[] sourceArray = ArrayManager.createIntArray(length, 100);
        quickSort(sourceArray, 0, length - 1);
        ArrayManager.printArray(sourceArray, ",");
    }

    public static void main(String[] args) {
        new QuickSort().quickSort();
    }

}
