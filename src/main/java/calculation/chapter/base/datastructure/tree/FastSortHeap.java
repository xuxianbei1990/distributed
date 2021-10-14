package calculation.chapter.base.datastructure.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 题8：修改堆中某个数，保证堆有序，整体时间复杂度是LogN
 *
 * @author: xuxianbei
 * Date: 2021/10/13
 * Time: 11:00
 * Version:V1.0
 */
public class FastSortHeap<T> {

    private ArrayList<T> heap;

    //记录值在堆上的位置
    private HashMap<T, Integer> indexMap;

    private Comparator<? super T> comparable;

    private int heapSize;

    public FastSortHeap(Comparator<? super T> comparable) {
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.comparable = comparable;
        this.heapSize = 0;
    }

    public boolean isEmpty() {
        return this.heapSize == 0;
    }

    public void put(T t) {
        heap.add(t);
        indexMap.put(t, heapSize);
        heapInsert(heapSize);
        heapSize++;
    }

    public T pop() {
        T result = heap.get(0);
        heapSize--;
        swap(0, heapSize);
        heap.remove(heapSize);
        indexMap.remove(result);
        heapify(0);
        return result;
    }

    private void heapify(int i) {
        int index = i;
        while (true) {
            int leftIndex = index * 2 + 1;
            int rightIndex = index * 2 + 2;
            if (leftIndex >= heapSize || rightIndex >= heapSize) {
                break;
            }
            T left = heap.get(leftIndex);
            T right = heap.get(rightIndex);
            int temp = comparable.compare(left, right) > 0 ? rightIndex : leftIndex;
            if (comparable.compare(heap.get(index), heap.get(temp)) > 0) {
                swap(index, temp);
            }
            index = temp;
            if (index >= heapSize) {
                break;
            }
        }
    }

    private void heapInsert(int index) {
        if (index == 0) return;
        while (index != 0) {
            int parent = (index - 1) / 2;
            if (comparable.compare(heap.get(parent), heap.get(index)) > 0) {
                swap(parent, index);
            }
            index = parent;
        }
    }

    private void swap(int parent, int index) {
        T t1 = heap.get(parent);
        T t2 = heap.get(index);
        heap.set(parent, t2);
        heap.set(index, t1);
        indexMap.put(t1, index);
        indexMap.put(t2, parent);
    }

    public boolean reSetValue(T t) {
        if (indexMap.containsKey(t)) {
            int index = indexMap.get(t);
            heapInsert(index);
            heapify(index);
        }
        return false;
    }

    public static void main(String[] args) {
        FastSortHeap<Student> fastSortHeap = new FastSortHeap(Comparator.comparingInt(Student::getAge));
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setAge((int) (Math.random() * 10));
            student.setName("张三" + i);
            fastSortHeap.put(student);
        }
        System.out.println(fastSortHeap.heap);
//        while (!fastSortHeap.isEmpty()) {
//            System.out.println(fastSortHeap.pop());
//        }
        Student studentTemp = fastSortHeap.heap.get(3);
        studentTemp.setAge(12);
        fastSortHeap.reSetValue(studentTemp);
        System.out.println(fastSortHeap.heap);

    }
}
