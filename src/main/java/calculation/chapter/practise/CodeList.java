package calculation.chapter.practise;

import calculation.chapter.base.datastructure.tree.MultiTreeHappy;
import calculation.chapter.base.datastructure.tree.TreeItem;
import calculation.chapter.base.link.Node;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import serializable.Student;

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



}
