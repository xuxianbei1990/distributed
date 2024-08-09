package calculation.chapter.likou.code1;

public class Code2Base2 {

    /**
     * 题目：有序数组查找num。有序数组查找最左的相等的数 2分查找
     * 局部最小值。
     *
     * @param array
     * @param num
     * @return
     */
    public static int search2(int[] array, int num) {
        search(0, array.length - 1, array, num, -1);
        return 0;
    }

    private static int search(int left, int right, int[] array, int num, int index) {
        if (left < right) {
            int mid = (left + right) / 2;
            if (array[mid] > num) {
                search(left, mid, array, num, index);
            } else if (array[mid] < num) {
                search(mid + 1, right, array, num, index);
            } else if (array[mid] == num) {
                search(left, mid - 1, array, num, mid);
            } else {
                return -1;
            }
        }
        return -1;
    }


}
