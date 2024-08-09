package calculation.chapter2;

/**
 * User: admin
 * Date: 2024/8/9
 * Time: 14:13
 * Version:V1.0
 */
public class PartSmall {


    public static int findPartMin(int[] array) {
        if (array == null) {
            return 0;
        }
        if (array.length == 1) {
            return 0;
        }
        if (array.length == 2) {
            return array[0] < array[1] ? 0 : 1;
        }
        int n = array.length;
        if (array[n - 1] < array[n - 2]) {
            return n - 1;
        }
        int l = 0;
        int r = n - 1;
        while (l < r - 1) {
            int mid = (l + r) / 2; // 防止溢出  int mid = l + (r - l) / 2;
            if (array[mid - 1] < array[mid] && array[mid] < array[mid + 1]) {
                return mid;
            }

            if (array[mid] <= array[l]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return array[l] < array[r] ? l : r;
    }

    public static void main(String[] args) {
        System.out.println(findPartMin(new int[]{6, 2, 3, 1, 5, 6}));
    }
}
