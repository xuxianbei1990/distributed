package calculation.chapter.base.datastructure.recursion;

/**
 * 在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和，求数组小和。
 * [1,3,4,2,5]
 * 1左边比1小的数，没有；
 * 3左边比3小的数，1；
 * 4左边比4小的数，1、3；
 * 2左边比2小的数，1；
 * 5左边比5小的数，1、3、4、2；
 * 所以小和为1+1+3+1+1+3+4+2=16
 *
 * @author: xuxianbei
 * Date: 2021/7/21
 * Time: 14:52
 * Version:V1.0
 */
public class MinNumSum {

    public static void main(String[] args) {
        MinNumSum minNumSum = new MinNumSum();
        System.out.println(minNumSum.execute(new int[]{1, 3, 4, 2, 5}));
    }

    public int execute(int[] array) {
        int[] temps = new int[array.length];
        return process(array, 0, array.length - 1, temps);
    }

    private int process(int[] array, int left, int size, int[] temps) {
        if (left < size) {
            int mid = (left + size) / 2;
            return process(array, left, mid, temps) + process(array, mid + 1, size, temps) +
                    merge(array, left, mid, size, temps);
        }
        return 0;
    }

    private int merge(int[] array, int i, int mid, int size, int[] temps) {
        int left = i;
        int rigth = mid + 1;
        int tempIndex = 0;
        int sum = 0;
        while (left <= mid && rigth <= size) {
            if (array[left] < array[rigth]) {
                sum = sum + array[left] * (size - rigth + 1);
                temps[tempIndex++] = array[left++];
            } else {
                temps[tempIndex++] = array[rigth++];
            }
        }
        while (left <= mid) {
            temps[tempIndex++] = array[left++];
        }

        while (rigth <= size) {
            temps[tempIndex++] = array[rigth++];
        }

        tempIndex = 0;
        left = i;
        while (left <= size) {
            array[left++] = temps[tempIndex++];
        }
        return sum;
    }
}
