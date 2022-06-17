package calculation.chapter.practise;

import calculation.chapter.base.link.Node;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import serializable.Student;

import javax.tools.StandardJavaFileManager;
import java.util.*;

/**
 * @author: xuxianbei
 * Date: 2022/6/13
 * Time: 9:44
 * Version:V1.0
 */
public class CodeList {


    private void code1(Node<Integer> header) {
        Node pred = null;
        Node next = null;
        while (header.getNext() != null) {
            pred = header.getNext();
            header.setNext(next);
            next = header;
            header = pred;
        }
        System.out.println(pred);
    }


    private Integer[] table;
    private Integer index;
    private Integer size;
    private Integer limit;

    private void init(Integer size) {
        table = new Integer[size];
        this.size = size;
        this.index = 0;
    }

    private void code2ArrayPut(Integer value) {
        if (index >= size) {
            return;
        }
        table[index] = value;
        index++;
    }

    private Integer code2ArrayPop() {
        if (index - 1 < 0) {
            return -1;
        }
        Integer result = table[index];
        table[index] = null;
        index--;
        return result;
    }


    private Integer in;
    private Integer out;

    private void initQueue(Integer size) {
        table = new Integer[size];
        in = 0;
        out = -1;
        this.size = 0;
        this.limit = size;
    }

    private void putQueue(Integer value) {
        if (in == out && size >= limit) {
            System.out.println("-1");
            return;
        }
        if (size >= limit) {
            System.out.println("-1");
            return;
        }
        if (in >= limit) {
            in = 0;
        }
        table[in] = value;
        in++;
        size++;
    }

    private Integer popQueue() {
        if (in == out && size <= 0) {
            return -1;
        }
        if (size <= 0) {
            return -1;
        }
        out++;
        Integer result = table[out];
        table[out] = null;
        if (out == limit - 1) {
            out = -1;
        }
        size--;
        return result;
    }

    private Integer arrayCode(int[] array, int left, int right) {
        if (left == right) {
            return array[left];
        }
        int mid = (left + right) / 2;
        Integer left1 = arrayCode(array, left, mid);
        Integer right1 = arrayCode(array, mid + 1, right);
        return Math.max(left1, right1);
    }

    private void arrayCodeSort(int[] array, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            arrayCodeSort(array, left, mid, temp);
            arrayCodeSort(array, mid + 1, right, temp);
            mergy(array, left, mid, right, temp);
        }
    }

    private void mergy(int[] array, int left, int mid, int right, int[] temp) {
        int left1 = left;
        int right1 = mid + 1;
        int length = right;
        int index = 0;
        while (left1 <= mid && right1 <= length) {
            if (array[left1] < array[right1]) {
                temp[index++] = array[left1++];
            } else {
                temp[index++] = array[right1++];
            }
        }
        while (left1 < mid) {
            temp[index++] = array[left1++];
        }
        while (right1 < length) {
            temp[index++] = array[right1++];
        }
        index = 0;
        while (left <= right) {
            array[left++] = temp[index++];
        }
    }

    private Integer arrayMinPair(int[] array, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            return arrayMinPair(array, left, mid, temp) +
                    arrayMinPair(array, mid + 1, right, temp) +
                    minPair(array, left, mid, right, temp);
        }
        return 0;
    }

    private Integer minPair(int[] array, int left, int mid, int right, int[] temp) {
        int left1 = left;
        int right1 = mid + 1;
        int index = 0;
        int sum = 0;
        while (left1 < mid && right1 < right) {
            if (array[left1] < array[right1]) {
                for (int p = right1; p <= right; p++) {
                    System.out.println(array[p] + ":" + array[left1]);
                }
                temp[index] = array[left1++];
                sum = sum + temp[index] * (right - right1 + 1);
            } else {
                temp[index] = array[right1++];
            }
            index++;
        }

        while (left1 < mid) {
            temp[index++] = array[left1++];
        }
        while (right1 < right) {
            temp[index++] = array[right1++];
        }
        index = 0;
        while (left <= right) {
            array[left++] = temp[index++];
        }
        return sum;
    }

    private void quickSort(int[] array) {
        for (int index = 1; index < array.length; index++) {
            int value = array[index];
            for (int i = index - 1; i >= 0; i--) {
                if (array[i] > value) {
                    swap(array, i, index);
                }
            }
        }

    }

    private void swap(int[] array, int i, int index) {
    }

    private void swap(Integer[] array, int i, int index) {
    }

    private void swap(List<Student> studentList, int i, int j) {

    }

    private void helanFlag(int[] array, int special) {
        int left = 0;
        int right = array.length - 1;
        int mid = 0;
        while (mid < array.length || mid < right) {
            if (array[mid] > special) {
                swap(array, mid, right);
                right--;
            } else if (array[mid] < special) {
                swap(array, mid, left);
                mid++;
                left++;
            } else {
                mid++;
            }
        }
    }

    private void init() {
        table = new Integer[8];
        this.size = 0;
        this.limit = 8;
    }

    private void treePut(int value) {
        if (size > limit) {
            return;
        }
        table[size++] = value;
    }

    private Integer getLeft(int index) {
        if (index * 2 + 1 > size) {
            return -1;
        }
        return table[index * 2 + 1];
    }

    private Integer getRight(int index) {
        if (index * 2 + 2 > size) {
            return -1;
        }
        return table[index * 2 + 2];
    }

    private Integer getParent(int index) {
        if (index == 0) {
            return table[index];
        }
        return table[(index - 1) / 2];
    }


    private void smallTreePut(int value) {
        if (size > limit) {
            return;
        }

        if (size == 0) {
            table[size++] = value;
        }

        if (getParent(size) <= value) {
            table[size++] = value;
        } else {
            int target = size;
            table[size++] = value;
            heapify(target);
        }

    }

    private void heapify(int parent) {
        while (parent > 0) {
            int newParent = (parent - 1) / 2;
            if (table[newParent] <= table[parent]) {
                break;
            } else {
                swap(table, newParent, parent);
                parent = newParent;
            }
        }
    }

    private int getMinValueAndRemove() {
        if (size <= 0) {
            return -1;
        }
        int result = table[0];
        size--;
        removeHeapify(0);
        return result;
    }

    private void removeHeapify(int i) {
        while (i < size) {
            int left = i * 2 + 1;
            if (left > size - 1) {
                break;
            }
            int right = i * 2 + 2;
            if (right > size - 1) {
                table[i] = table[left];
                break;
            }
            int head = table[left] > table[right] ? right : left;
            int son = head == right ? right : left;
            table[i] = table[head];
            i = son;
        }
    }

    private Map<Student, Integer> tableHashMap;
    //大根堆
    private List<Student> studentList = new ArrayList();

    private void initFastTree() {
        tableHashMap = new HashMap();
    }

    private void putFastTree(Student value) {
        studentList.add(value);
        tableHashMap.put(value, studentList.size() - 1);
        heapifyMax(size);

        size++;
    }

    private void heapifyMax(Integer index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (studentList.get(parent).getAge() >= studentList.get(index).getAge()) {
                break;
            } else {
                swap(studentList, parent, index);
                index = parent;
            }
        }
    }

    private Student popFastTree() {
        Student result = studentList.get(0);
        tableHashMap.remove(result);
        removeHeapifyFastTree(0);
        return result;
    }

    private void removeHeapifyFastTree(int i) {
        while (true) {
            Student left = getStudent(i * index + 1);
            Student right = getStudent(i * index + 2);
            if (right == null) {

            }
        }

    }

    private Student getStudent(int i) {
        if (i >= studentList.size()) {
            return null;
        }
        return studentList.get(i);
    }


    private void updateFastTree(Student value) {
        if (tableHashMap.get(value) != null) {
            Integer index = tableHashMap.get(value);
            heapifyMax(index);
            removeHeapifyFastTree(size);
        }
    }

    class PreNode {
        int pass = 0;
        int end = 0;
        char value = 0x00;
        PreNode[] nodes = new PreNode[26];
    }

    PreNode headerPre = new PreNode();

    private void preTreePut(String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        PreNode last = null;
        PreNode indexNode = headerPre;
        for (char item : value.toCharArray()) {
            indexNode.pass++;
            int index = containPre(indexNode.nodes, item);
            if (index > -1) {
                PreNode preNode1 = indexNode.nodes[index];
                preNode1.value = item;
                preNode1.pass++;
                last = preNode1;
                indexNode = preNode1;
            } else {
                last = null;
                break;
            }
        }
        if (last != null) {
            last.end++;
        }
    }

    //返回item的位置，如果不存在就返回空的位置, 无法识别的字符返回-1
    private int containPre(PreNode[] nodes, char item) {
        for (int i = 0; i < nodes.length; i++) {
//            int i = aChar - 'a';
            if (nodes[i].value == item) {
                return i;
            } else if (nodes[i].value == 0x00) {
                nodes[i] = new PreNode();
                return i;
            }
        }
        return -1;
    }

    private PreNode exist(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        PreNode indexNode = headerPre;
        for (char c : value.toCharArray()) {
            int index = containPre(indexNode.nodes, c);
            if (index == -1) {
                return null;
            }
            indexNode = indexNode.nodes[index];
        }
        return indexNode;
    }

    private void countSort(int[] array, int min, int max) {
        table = new Integer[max - min + 2];
        for (int i : array) {
            table[i]++;
        }
        int index = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] > 0) {
                for (int j = 0; j < table[i]; j++) {
                    array[index++] = i;
                }
            }
        }
        System.out.println(array);
    }


    private static void cardinalNumSort(int[] array, int bucket) {
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add(new ArrayList<>());
        }
        List<String> help = new ArrayList<>();
        for (int i : array) {
            help.add(String.format("%0" + bucket + "d", i));
        }

        for (int i = 1; i <= bucket; i++) {
            for (String s : help) {
               int key = Integer.valueOf(s.substring(bucket - i, bucket - i + 1));
               lists.get(key).add(s);
            }
            help.clear();
            for (List<String> list : lists) {
                if ((!CollectionUtils.isEmpty(list))) {
                    help.addAll(list);
                }
            }
            for (List<String> list : lists) {
                list.clear();
            }
        }
        System.out.println(help.toString());
    }

}
