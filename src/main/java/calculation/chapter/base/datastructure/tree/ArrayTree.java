package calculation.chapter.base.datastructure.tree;

/**
 * 题5：用数组构建树；左节点： 2 * i + 1; 右节点：2 * i + 2; 父节点 (i - 1)/2
 *
 * @author: xuxianbei
 * Date: 2021/7/29
 * Time: 19:45
 * Version:V1.0
 */
public class ArrayTree {
    /**
     * 数组
     */
    private int[] tableDate;

    /**
     * 长度
     */
    private int size = 0;

    /**
     * 下标
     */
    private int index = -1;

    private ArrayTree(int size) {
        tableDate = new int[size];
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return index == size - 1;
    }


    public boolean put(int i) {
        if (isFull()) {
            return false;
        }
        tableDate[index++] = i;
        return true;
    }

    public int get(int index) {
        if (isEmpty()) {
            return -1;
        }
        return tableDate[index];
    }

    public int getParent(int index) {
        return (index - 1) / 2;
    }

    public int getLeft(int index) {
        if (index * 2 + 1 <= size) {
            return tableDate[index * 2 + 1];
        }
        return -1;
    }

    public int getRight(int index) {
        if (index * 2 + 2 <= size) {
            return tableDate[index * 2 + 2];
        }
        return -1;
    }
}
