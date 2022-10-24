package calculation.chapter.practise;

import calculation.chapter.base.datastructure.graph.CollectionAnd;
import calculation.chapter.base.datastructure.tree.MultiTreeHappy;
import calculation.chapter.base.datastructure.tree.TreeItem;
import calculation.chapter.base.link.Node;
import calculation.chapter.two.MergeSort;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import serializable.Student;

import java.util.*;
import java.util.stream.Collectors;

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

    class LinkedNode {
        LinkedNode next;
        String value;
    }

    private void fastSlowNode(LinkedNode header) {
        if (header == null || header.next == null || header.next.next == null) {
            return;
        }
        LinkedNode slow = header.next;
        LinkedNode fast = header.next.next;
        while (slow.next != null && fast.next.next != null) {
            slow = header.next;
            fast = header.next.next;
        }
        System.out.println(slow);
    }

    private boolean judgePalindromeStruct(LinkedNode header) {
        if (header == null) {
            return false;
        }
        Stack<LinkedNode> stringStack = new Stack<>();
        LinkedNode index = header;
        while (index != null) {
            stringStack.push(index);
            index = index.next;
        }

        index = header;
        while (!stringStack.isEmpty()) {
            LinkedNode value = stringStack.pop();
            if (!value.equals(index)) {
                return false;
            }
            index = index.next;
        }
        return true;
    }


    @Data
    static class RandomNode {
        int value;
        RandomNode next;
        RandomNode rand;

        public RandomNode(int value) {
            this.value = value;
        }
    }

    private RandomNode copy(RandomNode header) {
        if (header == null) {
            return null;
        }
        Map<RandomNode, RandomNode> map = new HashMap<>();
        RandomNode index = header;
        while (index != null) {
            map.put(index, new RandomNode(index.value));
            index = index.next;
        }
        index = header;
        RandomNode header1 = map.get(index);
        RandomNode index1 = header1;
        while (index != null) {
            index1.setNext(map.get(index.next));
            index1.setRand(map.get(index.rand));
            index = index.next;
            index1 = index1.getNext();
        }
        return header1;
    }

    private void forTree(TreeItem treeItem) {
        if (treeItem == null) {
            return;
        }
        //System.out.println("Pre")
        forTree(treeItem.getLeft());
        //System.out.println("mid")
        forTree(treeItem.getRight());
        //System.out.println("after")
    }

    private void stackForTree1(TreeItem treeItem) {
        if (treeItem == null) {
            return;
        }
        Stack<TreeItem> stack = new Stack<>();
        TreeItem index = treeItem;
        stack.push(index);
        while (!stack.isEmpty()) {
            index = stack.pop();
            if (index != null) {
                System.out.println(index.getData());
            }
            if (index.getRight() != null) {
                index = index.getRight();
                stack.push(index);
            }
            if (index.getLeft() != null) {
                index = index.getLeft();
                stack.push(index);
            }
        }
    }


    private void stackForTree2(TreeItem treeItem) {
        Stack<TreeItem> stack = new Stack<>();
        while (treeItem != null && !stack.isEmpty()) {
            if (treeItem != null) {
                stack.push(treeItem);
                if (treeItem.getLeft() != null) {
                    treeItem = treeItem.getLeft();
                }
            } else {
                treeItem = stack.pop();
                System.out.println(treeItem.getData());
                treeItem = treeItem.getRight();
            }
        }
    }

    private void stackForTree3(TreeItem treeItem) {
        Stack<TreeItem> stack1 = new Stack<>();
        Stack<TreeItem> stack2 = new Stack<>();
        stack1.push(treeItem);
        while (treeItem != null) {
            TreeItem treeItem1 = stack1.pop();
            if (treeItem1.getLeft() != null) {
                stack1.push(treeItem1.getLeft());
            }
            if (treeItem1.getRight() != null) {
                stack1.push(treeItem1.getRight());
            }
            stack2.push(treeItem1);
        }
        while (!stack2.isEmpty()) {
            System.out.println(stack2.pop());
        }
    }

    private void horizontalTraverse(TreeItem treeItem) {
        LinkedList<TreeItem> linkedList = new LinkedList();
        linkedList.push(treeItem);
        while (!linkedList.isEmpty()) {
            TreeItem index = linkedList.pop();
            System.out.println(index.getData());
            if (index.getLeft() != null) {
                linkedList.push(index.getLeft());
            }
            if (index.getRight() != null) {
                linkedList.push(index.getRight());
            }
        }
    }

    private void maxStoreyNodeNum(TreeItem treeItem) {
        LinkedList<TreeItem> linkedList = new LinkedList<>();
        Map<TreeItem, Integer> map = new HashMap<>();
        int cur = 0;
        int max = 0;
        int maxStorey = 0;
        map.put(treeItem, cur);
        while (!linkedList.isEmpty()) {
            TreeItem index = linkedList.pop();
            Integer temp = map.get(index);
            if (temp == cur) {
                max++;
            } else {
                cur++;
                maxStorey = Math.max(max, maxStorey);
                max = 1;
            }
            temp++;
            if (index.getLeft() != null) {
                linkedList.push(index.getLeft());
                map.put(index.getLeft(), temp);
            }
            if (index.getRight() != null) {
                linkedList.push(index.getRight());
                map.put(index.getRight(), temp);
            }
        }
        maxStorey = Math.max(max, maxStorey);
        System.out.println(maxStorey);
    }

    private void maxStoreyNodeNum2(TreeItem treeItem) {
        LinkedList<TreeItem> treeItems = new LinkedList<>();
        treeItems.push(treeItem);
        TreeItem currentNode = treeItem;
        TreeItem rightEndNodex = null;
        int sum = 0;
        int max = 0;
        while (!treeItems.isEmpty()) {
            TreeItem index = treeItems.pop();
            sum++;
            if (index.getLeft() != null) {
                rightEndNodex = index.getLeft();
                treeItems.push(rightEndNodex);
            }
            if (index.getRight() != null) {
                rightEndNodex = index.getRight();
                treeItems.push(rightEndNodex);
            }
            if (index == currentNode) {
                currentNode = rightEndNodex;
                max = Math.max(max, sum);
                sum = 0;
            }
        }
        max = Math.max(max, sum);
        System.out.println(max);
    }

    private void serializableTree(TreeItem<Integer> treeItem) {
        Queue<Integer> list = new LinkedList<>();
        preTree(treeItem, list);
        System.out.println(JSONObject.toJSON(list));
    }

    private void preTree(TreeItem<Integer> treeItem, Queue<Integer> list) {
        if (treeItem == null) {
            return;
        }
        list.add(treeItem.getData());
        preTree(treeItem.getLeft(), list);
        preTree(treeItem.getRight(), list);
    }

    private void unSerializableTree(Queue<Integer> list) {
        TreeItem treeItem = unPreTree(list);
    }

    private TreeItem unPreTree(Queue<Integer> queue) {
        Integer index = queue.poll();
        if (index == null) {
            return null;
        }
        TreeItem treeItem = new TreeItem(index);
        treeItem.setLeft(unPreTree(queue));
        treeItem.setRight(unPreTree(queue));
        return treeItem;
    }

    private void printTree(TreeItem<Integer> treeItem, Integer level) {
        if (treeItem == null) {
            return;
        }
        printTree(treeItem.getRight(), ++level);
        System.out.println(treeItem.getData());
        printTree(treeItem.getLeft(), level);
    }

    private TreeItem nextTreeNode(TreeItem treeItem) {
        if (treeItem == null) {
            return null;
        }

        if (treeItem.getRight() != null) {
            TreeItem index = treeItem.getRight();
            while (index.getLeft() != null) {
                index = index.getLeft();
            }
            return index;
        }
        if (treeItem == treeItem.getParent().getRight()) {
            TreeItem index = treeItem.getParent();
            while (index == index.getParent().getRight()) {
                index = index.getParent();
            }
            return index.getParent();
        }
        if (treeItem == treeItem.getParent().getLeft()) {
            return treeItem.getParent();
        }
        return null;
    }


    private static void paperHalfOff(int n) {
        if (n == 0) {
            return;
        }
//        TreeItem<String> treeItem = new TreeItem<>("凹");
//        fillTreeItem(treeItem, 0, n);

        midPaper(0, n, "凹");
    }

    private static void midPaper(int i, int n, String value) {
        if (i >= n) {
            return;
        }
        midPaper(++i, n, "凹");
        System.out.println(value);
        midPaper(i, n, "凸");
    }

    private static TreeItem fillTreeItem(TreeItem<String> treeItem, int i, int storey) {
        if (i >= storey) {
            return null;
        }
        treeItem.setLeft(fillTreeItem(new TreeItem<>("凹"), ++i, storey));
        treeItem.setRight(fillTreeItem(new TreeItem<>("凸"), i, storey));
        return treeItem;
    }


    static class BalanceTreeInfo {
        Integer height = 0;
        boolean balance;
    }

    private static boolean isBalanceTree(TreeItem<Integer> treeItem) {
        BalanceTreeInfo balanceTreeInfo = process(treeItem);
        return balanceTreeInfo.balance;
    }

    private static BalanceTreeInfo process(TreeItem<Integer> treeItem) {
        if (treeItem == null) {
            BalanceTreeInfo balanceTreeInfo = new BalanceTreeInfo();
            balanceTreeInfo.balance = true;
            balanceTreeInfo.height = 0;
            return balanceTreeInfo;
        }
        BalanceTreeInfo balanceTreeInfo = new BalanceTreeInfo();
        BalanceTreeInfo balanceTreeInfoLeft = process(treeItem.getLeft());
        BalanceTreeInfo balanceTreeInfoRight = process(treeItem.getRight());
        if (balanceTreeInfoLeft.balance && balanceTreeInfoRight.balance
                && Math.abs(balanceTreeInfoLeft.height - balanceTreeInfoRight.height) <= 1) {
            balanceTreeInfo.balance = true;
        } else {
            balanceTreeInfo.balance = false;
        }
        balanceTreeInfo.height = Math.max(balanceTreeInfoLeft.height, balanceTreeInfoLeft.height) + 1;
        return balanceTreeInfo;
    }

    static class DistanceTreeInfo {
        int height;
        int maxDistance;
    }

    private static Integer distanceTree(TreeItem treeItem) {
        DistanceTreeInfo resutl = process2(treeItem);
        return resutl.maxDistance;
    }

    private static DistanceTreeInfo process2(TreeItem treeItem) {
        if (treeItem == null) {
            DistanceTreeInfo distanceTreeInfo = new DistanceTreeInfo();
            distanceTreeInfo.height = 0;
            distanceTreeInfo.maxDistance = 0;
            return distanceTreeInfo;
        }
        DistanceTreeInfo distanceTreeInfo = new DistanceTreeInfo();
        DistanceTreeInfo distanceTreeInfoLeft = process2(treeItem.getLeft());
        DistanceTreeInfo distanceTreeInfoRight = process2(treeItem.getRight());
        distanceTreeInfo.height = distanceTreeInfoLeft.height + 1;
        distanceTreeInfo.maxDistance = Math.max(Math.max(distanceTreeInfoLeft.maxDistance, distanceTreeInfoRight.maxDistance),
                distanceTreeInfoLeft.height + distanceTreeInfoRight.height + 1);
        return distanceTreeInfo;
    }

    static class MaxSonTree {
        boolean isSearch;
        TreeItem<Integer> header;
        Integer min = 0;
        Integer max = 0;
        Integer sum = 0;

        public MaxSonTree(boolean isSearch) {
            this.isSearch = isSearch;
        }
    }

    private static MaxSonTree maxSonTree(TreeItem<Integer> header) {
        return process3(header);
    }

    private static MaxSonTree process3(TreeItem<Integer> header) {
        if (header == null) {
            return new MaxSonTree(true);
        }
        if (header.getData() == null) {
            header.setData(0);
        }
        MaxSonTree left = process3(header.getLeft());
        MaxSonTree right = process3(header.getRight());
        MaxSonTree maxSonTree = new MaxSonTree(false);
        maxSonTree.max = Math.max(right.max, header.getData());
        maxSonTree.min = Math.max(left.min, header.getData());

        if (left.header == null && right.header == null) {
            maxSonTree.isSearch = true;
        }
        if (left.isSearch && right.isSearch && header.getData() >= left.max &&
                header.getData() <= right.min) {
            maxSonTree.isSearch = true;
        } else {
            int sum = Math.max(left.sum, right.sum);
            maxSonTree.header = sum == left.sum ? left.header : right.header;
        }
        if (maxSonTree.isSearch) {
            maxSonTree.header = header;
            maxSonTree.sum = left.sum + right.sum + 1;
        }
        return maxSonTree;
    }


    @Data
    static class EmployeeHappy {
        Integer happy;
        List<EmployeeHappy> subs = new ArrayList<>();

        public EmployeeHappy(Integer happy) {
            this.happy = happy;
        }
    }

    @Data
    static class HappyInfo {
        private Integer happy;
        private Integer happySum = 0;

        public HappyInfo(Integer happy) {
            this.happy = happy;
        }
    }

    private static HappyInfo maxHappy(EmployeeHappy header) {
        if (header == null) {
            return new HappyInfo(0);
        }
        if (CollectionUtils.isEmpty(header.getSubs())) {
            return new HappyInfo(header.getHappy());
        }
        List<HappyInfo> list = new ArrayList<>();
        for (EmployeeHappy sub : header.getSubs()) {
            HappyInfo happyInfo = maxHappy(sub);
            list.add(happyInfo);
        }
        Integer sum = list.stream().map(HappyInfo::getHappy).reduce(Integer::sum).orElse(0);
        HappyInfo info = new HappyInfo(0);
        info.setHappySum(info.getHappySum() + Math.max(header.getHappy(), sum));
        return info;
    }


    static class FullTreeInfo {
        boolean is;

        FullTreeInfo(boolean is) {
            this.is = is;
        }
    }

    private static FullTreeInfo fullTreeVertify(TreeItem<Integer> header) {
        if (header == null) {
            return new FullTreeInfo(false);
        }
        FullTreeInfo l = fullTreeVertify(header.getLeft());
        FullTreeInfo r = fullTreeVertify(header.getRight());
        FullTreeInfo fullTreeInfo = new FullTreeInfo(false);
        if (header.getLeft() == null && header.getRight() == null) {
            fullTreeInfo.is = true;
        } else {
            fullTreeInfo.is = l.is && r.is;
        }
        return fullTreeInfo;
    }

    private static TreeItem<Integer> minCommonAncetors(TreeItem<Integer> header, TreeItem<Integer> a, TreeItem<Integer> b) {
        Map<TreeItem<Integer>, TreeItem<Integer>> map = new HashMap<>();
        map.put(header, null);
        process4(header, map);
        Set<TreeItem<Integer>> set = new HashSet<>();
        TreeItem<Integer> index = map.get(a);
        while (index != null) {
            set.add(index);
            index = map.get(index);
        }
        index = map.get(b);
        while (index != null) {
            if (!set.add(index)) {
                return index;
            }
            index = map.get(index);
        }
        return null;
    }

    private static void process4(TreeItem<Integer> header, Map<TreeItem<Integer>, TreeItem<Integer>> map) {
        if (header == null) {
            return;
        }
        process4(header.getLeft(), map);
        if (header.getLeft() != null) {
            map.put(header.getLeft(), header);
        }
        process4(header.getRight(), map);
        if (header.getRight() != null) {
            map.put(header.getRight(), header);
        }
    }


    static class Ancetors {
        boolean findO1;
        boolean find02;
        TreeItem<Integer> header;
    }

    private static TreeItem<Integer> minCommonAncetors2(TreeItem<Integer> header, TreeItem<Integer> a, TreeItem<Integer> b) {
        return process5(header, a, b).header;
    }

    private static Ancetors process5(TreeItem<Integer> header, TreeItem<Integer> a, TreeItem<Integer> b) {
        if (header == null) {
            return new Ancetors();
        }
        Ancetors ancetorsL = process5(header.getLeft(), a, b);
        Ancetors ancetorsR = process5(header.getRight(), a, b);
        Ancetors ancetors = new Ancetors();
        boolean find01 = header == a || ancetorsL.findO1 || ancetorsR.find02;
        boolean find02 = header == b || ancetorsL.find02 || ancetorsR.find02;

        if (ancetorsL.find02 && ancetorsL.findO1) {
            ancetors.header = ancetorsL.header;
        }

        if (ancetorsR.find02 && ancetorsR.findO1) {
            ancetors.header = ancetorsR.header;
        }

        if ((ancetors.header == null) && find01 && find02) {
            ancetors.header = header;
        }
        return ancetors;
    }

    private static String minStr(String value) {
        List<String> list = new ArrayList<>();
        for (char c : value.toCharArray()) {
            list.add(String.valueOf(c));
        }
        list.sort((a, b) -> a.getBytes()[0] >= b.getBytes()[0] ? 1 : -1);
        return list.toString();
    }

    private static void minArrayStr(String[] value) {
        /**
         * 1.获取最大长度
         * 2.填充值
         * 3.排序
         * 4.组合
         */
        int length = getMaxLength(value);
        fillValue(value, length);
        MergeSortStr(value);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : value) {
            s = s.replace("0", "");
            stringBuilder.append(s);
        }
        System.out.println(stringBuilder.toString());
    }

    private static void MergeSortStr(String[] value) {
        String[] temp = new String[value.length];
        process6(value, 0, value.length - 1, temp);
    }

    private static void process6(String[] value, int i, int length, String[] temp) {
        if (i < length) {
            int mid = (i + length) / 2;
            process6(value, i, mid, temp);
            process6(value, mid + 1, length, temp);
            mergeStr(value, i, mid + 1, length, temp);
        }
    }

    private static void mergeStr(String[] value, int left, int mid, int length, String[] temp) {
        int p1 = left;
        int p2 = mid;
        int index = 0;
        while (p1 < mid && p2 <= length) {
            if (value[p1].compareTo(value[p2]) > 0) {
                temp[index++] = value[p2++];
            } else {
                temp[index++] = value[p1++];
            }
        }

        while (p1 < mid) {
            temp[index++] = value[p1++];
        }

        while (p2 <= length) {
            temp[index++] = value[p2++];
        }
        p1 = left;
        index = 0;
        while (p1 <= length) {
            value[p1++] = temp[index++];
        }
    }

    private static void fillValue(String[] value, int length) {
        for (int i = 0; i < value.length; i++) {
            if (value[i].length() < length) {
                for (int j = value[i].length(); j < length; j++) {
                    value[i] = value[i] + "0";
                }
            }
        }
    }

    private static int getMaxLength(String[] value) {
        Integer max = 0;
        for (String s : value) {
            max = Math.max(max, s.length());
        }
        return max;
    }

    private static void lightALamp(String value) {
        String light = ".";
        int i = 0;
        int lightSum = 0;
        while (i < value.length()) {
            int scene = 0;
            if (String.valueOf(value.toCharArray()[i]).equals(light)) {
                scene = 1;
                i++;
                if (String.valueOf(value.toCharArray()[i]).equals(light)) {
                    scene = 2;
                    i++;
                    if (String.valueOf(value.toCharArray()[i]).equals(light)) {
                        scene = 3;
                        i++;
                    }
                }
            } else {
                i++;
            }
            if (scene > 0) {
                lightSum++;
            }
        }
        System.out.println(lightSum);
    }

    static class MaxItemInfo {
        int cost;
        int profit;
    }

    private static void maxItem(int[] costs, int[] profit, int k, int m) {
        List<MaxItemInfo> listCost = new ArrayList<>();
        for (int i = 0; i < costs.length; i++) {
            MaxItemInfo maxItemInfo = new MaxItemInfo();
            maxItemInfo.cost = costs[i];
            maxItemInfo.profit = profit[i];
            listCost.add(maxItemInfo);
        }
        listCost.sort((a, b) -> a.cost > b.cost ? 1 : -1);
        int items = 0;
        while (items < k) {
            List<MaxItemInfo> target = new ArrayList<>();
            for (MaxItemInfo maxItemInfo : listCost) {
                if (maxItemInfo.cost < m) {
                    target.add(maxItemInfo);
                }
            }
            target.sort((a, b) -> a.profit > b.profit ? -1 : 1);
            MaxItemInfo itemInfo = target.get(0);
            listCost.remove(itemInfo);
            int profit1 = itemInfo.profit;
            m = profit1 + m;
            items++;
        }
        System.out.println(m);
    }

    static Map<Integer, Node> valueMap = new HashMap<>();
    static Map<Node, Node> parentMap = new HashMap<>();
    static Map<Node, Integer> sizeMap = new HashMap<>();

    public static void add(Integer value) {
        Node<Integer> node = new Node();
        node.setData(value);
        valueMap.put(value, node);
        parentMap.put(node, node);
        sizeMap.put(node, 1);
    }

    public static void union(Integer a, Integer b) {
        Node node1 = valueMap.get(a);
        Node node2 = valueMap.get(b);
        if (node1 == null || node2 == null) {
            return;
        }
        Integer size1 = sizeMap.get(node1);
        Integer size2 = sizeMap.get(node2);
        Node minNode = size1 <= size2 ? node1 : node2;
        Node maxNode = size1 > size2 ? node1 : node2;
        parentMap.put(minNode, maxNode);
        sizeMap.put(maxNode, size1 + size2);
        sizeMap.remove(minNode);
    }

    public static Node findFather(Node value) {
        Node nodeIndex = parentMap.get(value);
        while (nodeIndex != value) {
            value = nodeIndex;
            nodeIndex = parentMap.get(nodeIndex);
        }
        return nodeIndex;
    }

    public static boolean isSameSet(Integer a, Integer b) {
        Node<Integer> nodeA = valueMap.get(a);
        Node<Integer> nodeB = valueMap.get(b);
        if (nodeA == null || nodeB == null) {
            return false;
        }
        return findFather(nodeA) == findFather(nodeB);
    }


    static class GraphNode {
        String value;
        Integer in;
        Integer out;
        List<GraphNode> nexts;
        List<GraphEdge> edges;

        GraphNode(String value, Integer in, Integer out, List<GraphNode> nodes, List<GraphEdge> edges) {
            this.value = value;
            this.in = in;
            this.out = out;
            this.nexts = nodes;
            this.edges = edges;
        }
    }

    static class GraphEdge {
        Integer weight;
        GraphNode from;
        GraphNode to;

        GraphEdge(Integer weight, GraphNode from, GraphNode to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }

    private static void horizontalFor(GraphNode header) {
        LinkedList<GraphNode> linkedList = new LinkedList<>();
        Set<GraphNode> set = new HashSet<>();
        set.add(header);
        linkedList.push(header);
        while (!linkedList.isEmpty()) {
            GraphNode index = linkedList.pop();
            System.out.println(index.value);
            for (GraphNode next : index.nexts) {
                if (set.add(next)) {
                    linkedList.push(next);
                }
            }
        }
    }

    private static void stackFor(GraphNode header) {
        Stack<GraphNode> stack = new Stack<>();
        Set<GraphNode> set = new HashSet<>();
        stack.push(header);
        set.add(header);
        while (!stack.isEmpty()) {
            GraphNode index = stack.pop();
            System.out.println(index.value);
            for (GraphNode next : index.nexts) {
                if (set.add(next)) {
                    stack.push(next);
                }
            }
        }
    }

    private static void tuoputu(GraphNode header) {
        /**
         * 1.从第一个节点获取值，放入到list中。记A
         * 2.遍历A。如果是In是0。从list中移除。记B
         * 3.获取B所有的子节点。更新in--;
         * 4.周而复始，直到list为空
         */
    }


    private static void kruskal(List<GraphEdge> edges) {
        edges.sort((a, b) -> a.weight > b.weight ? 1 : -1);
        Set<GraphNode> set = new HashSet<>();
        for (GraphEdge edge : edges) {
            if (set.contains(edge.from) && set.contains(edge.to)) {
                continue;
            }
            set.add(edge.from);
            set.add(edge.to);
            System.out.println(edge.weight);
        }
    }

    private static void p(GraphNode node) {
        Set<GraphNode> set = new HashSet<>();
        GraphNode index = node;
        while (index != null) {
            List<GraphEdge> edges = index.edges;
            GraphEdge edge = getMinEdge(edges);
            if (set.add(edge.to)) {
                System.out.println(edge.weight);
                index = edge.to;
            } else {
                index = null;
            }
        }
    }

    private static GraphEdge getMinEdge(List<GraphEdge> edges) {
        edges.sort((a, b) -> a.weight > b.weight ? 1 : -1);
        return edges.get(0);
    }

    /**
     * 1.指定一个点A。遍历循环列表C所有点到A距离。-1代表正无穷。记录距离最近的点B，其实就是A
     * 2.剔除A,从余下的节点中找到距离A最近的距离的节点记为D， 距离记为D1
     * 3.遍历循环列表C，规则 距离D的点的距离F，记点F1，取最小值： F+D1 和 F1 的距离做对比，取最小值。
     * 4.依次以上遍历。
     */
    private static void minDistance(List<GraphNode> graphNodes, GraphNode nodeA) {
        Map<GraphNode, Integer> map = new HashMap<>();
        for (GraphNode graphNode : graphNodes) {
            Integer distance = getDistance(graphNode, nodeA);
            map.put(graphNode, distance);
        }
        GraphNode index = nodeA;
        while (graphNodes.size() >= 1) {
            graphNodes.remove(index);
            GraphNode minIndex = getMinNode(graphNodes);
            for (GraphNode graphNode : graphNodes) {
                if (minIndex != graphNode) {
                    Integer distance = getDistance(graphNode, minIndex);
                    Integer distance2 = map.get(minIndex);
                    Integer distance3 = map.get(graphNode);
                    if (distance != -1 && distance2 != -1 && distance3 != -1) {
                        if (distance2 + distance > distance3) {
                            map.put(graphNode, distance3);
                        }
                    } else if (distance != -1) {
                        map.put(graphNode, distance);
                    }
                }
            }
            index = minIndex;
        }
    }

    private static GraphNode getMinNode(List<GraphNode> graphNodes) {
        return null;
    }

    private static Integer getDistance(GraphNode graphNode, GraphNode nodeA) {
        return null;
    }

    private static void minDistanceEx(List<GraphNode> graphNodes, GraphNode nodeA, GraphNode nodeB) {
        Map<GraphNode, Integer> map = new HashMap<>();
        for (GraphNode graphNode : graphNodes) {
            Integer distance = getDistance(graphNode, nodeA);
            map.put(graphNode, distance);
        }

        for (GraphNode graphNode : graphNodes) {
            for (GraphNode node : graphNodes) {
                if (graphNode == node) {
                    continue;
                }
                Integer dis = getDistance(graphNode, node);
                if (dis != -1) {
                    Integer dis1 = map.get(graphNode);
                    Integer dis2 = map.get(node);
                    if (dis1 > -1 && dis2 > -1) {
                        map.put(node, Math.min(dis + dis1, dis2));
                    } else if (dis1 > -1) {
                        map.put(node, dis + dis1);
                    }
                }
            }
        }
        System.out.println(map.get(nodeB));
    }


    private static void hannuota(Integer storey) {
        hannuota("A", "C", storey, storey);
    }

    private static void hannuota(String from, String to, Integer storey, Integer cur) {
        if (storey == 1) {
            if (from.equals("A") && to.equals("C")) {
                System.out.println(storey + "A->B, B->C");
            }
            if (from.equals("C") && to.equals("A")) {
                System.out.println(storey + "C->B, B->A");
            }
        } else if (storey == 2) {
            storey--;
            if (from.equals("A") && to.equals("C")) {
                hannuota("A", "C", storey, cur);
                System.out.println("2:A->B");
                hannuota("A", "C", storey, cur);
                System.out.println("2:B->C");
                hannuota("A", "C", storey, cur);
            }
            if (from.equals("C") && to.equals("A")) {
                hannuota("C", "A", storey, cur);
                System.out.println("2:C->B");
                hannuota("A", "C", storey, cur);
                System.out.println("2:B->A");
            }
        } else {
            storey--;
            hannuota("A", "C", storey, cur);
            System.out.println(cur + "A" + "->" + "B");
            hannuota("C", "A", storey, cur);
            System.out.println(cur + "B" + "->" + "C");
            hannuota("A", "C", storey, cur);
            cur--;
        }
    }

    private static void hannuota2(String from, String to, String other, Integer storey) {
        if (storey < 1) {
            return;
        }
        if (storey == 1) {
            System.out.println(storey + ":" + from + "->" + to + "->" + other);
        } else {
            hannuota2(from, to, other, storey - 1);
            System.out.println(storey + ":" + from + "->" + to);
            hannuota2(other, to, from, storey - 1);
            System.out.println(storey + ":" + to + "->" + other);
            hannuota2(from, to, other, storey - 1);
        }
    }

    private static void printSon(String values) {
        for (int i = 0; i < values.toCharArray().length; i++) {
            for (int j = i + 1; j < values.length(); j++) {
                System.out.println((new String(new char[]{values.toCharArray()[i]})) + values.substring(i + 1, j + 1));
            }
        }
    }

    private static void printAllSon(String value) {
        List<String> ans = new ArrayList<>();
        String path = "";
        Integer index = 0;
        process7(value.toCharArray(), ans, index, path);
        System.out.println(ans);
    }

    private static void process7(char[] cs, List<String> ans, Integer index, String path) {
        if (index == cs.length) {
            ans.add(path);
            return;
        }
        String trues = cs[index] + path;
        process7(cs, ans, index + 1, trues);
        String no = path;
        process7(cs, ans, index + 1, no);
    }


    private static void printAllSort(String values) {
        char[] chars = values.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            char[] chars1 = new char[i];
            processSort(chars, i, 0, -1, chars1);
        }
    }

    private static void processSort(char[] chars, int num, int begin, int index, char[] chars1) {
        if (num == 0) {
            return;
        }
        num--;
        index++;
        for (int i1 = begin; i1 < chars.length; i1++) {
            chars1[index] = chars[i1];
            processSort(chars, num, i1 + 1, index, chars1);
            if (num == 0) {
                System.out.println(new String(chars1));
            }
        }
    }

    private static void printAll1(String values) {
        List<String> ans = new ArrayList<>();
        ans.add(values);
        process8(values.toCharArray(), 0, ans);
    }

    private static void process8(char[] toCharArray, int index, List<String> ans) {
        if (index >= toCharArray.length) {
            return;
        }
        for (int i = 0; i < toCharArray.length; i++) {
            if (index != i) {
                String value = swap(index, i, toCharArray);
                ans.add(value);
            }
        }
        index++;
        process8(toCharArray, index, ans);


    }

    private static String swap(int c, int i, char[] toCharArray) {
        return "";
    }

    private static void process8compare(char[] str, int i, List<String> ans) {
        if (i == str.length) {
            ans.add(String.valueOf(str));
        }
        boolean[] visit = new boolean[26];
        for (int j = i; j < str.length; j++) {
            if (!visit[str[j] - 'a']) {
                visit[str[j] - 'a'] = true;
                swap(str, i, j);
                process8compare(str, i + 1, ans);
                swap(str, i, j);
            }
        }
    }

    private static void swap(char[] str, int i, int j) {
        char c = str[i];
        str[i] = str[j];
        str[j] = c;
    }

    private static void unSortStack(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        Integer value = popLastOne(stack);
        unSortStack(stack);
        stack.push(value);
    }


    private static Integer popLastOne(Stack<Integer> stack) {
        Integer value = stack.pop();
        if (stack.isEmpty()) {
            return value;
        } else {
            Integer value2 = popLastOne(stack);
            stack.push(value);
            return value2;
        }
    }

}
