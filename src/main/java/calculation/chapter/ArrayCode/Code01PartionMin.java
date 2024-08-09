package calculation.chapter.ArrayCode;

import java.util.*;

public class Code01PartionMin {


    public static void main(String[] args) {
        Code01PartionMin code01PartionMin = new Code01PartionMin();
//        Node root = new Node(1, new Node(2, new Node(4), new Node(5), null),
//                new Node(3, new Node(6), new Node(7), null), null);
        int[]  test = new int[]{4,19,14,5,-3,1,8,5,11,15};
        ListNode head = new ListNode(test[0]);
        ListNode loop = head;
        for (int i = 1; i< test.length; i++){
            loop.next = new ListNode(test[i]);
            loop = loop.next;
        }
        int ans = 3;

        System.out.println(code01PartionMin.findKthLargest(new int[]{3,2,1,5,6,4}, 2));



    }
    public int findKthLargest(int[] nums, int k) {
        int i = nums[0];
        int l = -1;
        int r = nums.length;
        int index = 0;
        while(nums.length - index != k){
            while(index < r){
                if (nums[index] > i){
                    swrap(index, r - 1, nums);
                    r--;
                }else if (nums[index] < i){
                    swrap(index, l + 1, nums);
                    l++;
                    index++;
                }else {
                    index++;
                }
            }
            if (nums.length - index == k) {
                break;
            }
            if (nums.length - k  < index) {
                i = nums[l - k + 1];
                r = l;
                l = l - k;
            } else {
                i = nums[r];
                l =  r - 1;
                r = nums.length;
            }
            index= l + 1;

        }
        return nums[index];
    }

    private void swrap(int a, int b, int[] nums){
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }


    public static int div2(int a, int b) {
        int x  = a;
        int y = b;
        int res = 0;
        for (int i = 30; i >= 0; i --) {
            if ((x >> i) >= y) {
                res = res | (1 << i);
                x = x- (y << i);
            }
        }
        return res;
    }

    private int div (int a, int b) {
        int ans = 0;
        for (int i = 30; i >= 0; i--) {
            if ((a >> i) >= b) {
                ans = ans + (1 << i);
                a = a - (b << i);
            }
        }
        return ans;
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    ;

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        MyLinkedList queue = new MyLinkedList();
        queue.add(root);
        int times = 0;
        Node pre = null;
        while(!queue.isEmpty()) {
            times = queue.size;
            pre = null;
            for (int i = 0; i< times; i++){
                Node node1 = queue.pop();
                if (pre != null) {
                    pre.next = node1;
                }
                pre = node1;

                if (node1.left != null){
                    queue.add(node1.left);

                }
                if (node1.right != null) {
                    queue.add(node1.right);

                }
            }
        }
        return root;
    }

    static class MyLinkedList {
        Node header;
        Node tail;
        int size = 0;

        public boolean isEmpty() {
            return size == 0;
        }

        public MyLinkedList() {
            this.header = null;
            this.tail = null;
            size =0;
        }

        public void add(Node node1) {
            if (header == null){
                header = node1;
                tail = node1;

            } else {
                tail.next = node1;
                tail = node1;
            }
            size++;

        }

        public Node pop() {
            Node temp = header;
            header = header.next;
            temp.next = null;
            size--;
            return temp;
        }
    }

    public boolean exist(char[][] board, String word) {

        char[] words = word.toCharArray();
        char c1 = words[0];
        int index = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                if (board[i][j] == c1) {
                    boolean flag = true;
                    while (flag) {
                        index++;
                        char c2 = words[index];
                        flag = find2(board, c2, i, j);
                    }
                    if (flag) {
                        return flag;
                    }
                    index = 0;
                }
            }
        }
        return false;
    }


    private boolean find2(char[][] board, char c1, int i, int j) {
        return (i - 1 > 0 && board[i - 1][j] == c1) || (
                j - 1 > 0 && board[i][j - 1] == c1) || (
                i + 1 < board.length && board[i + 1][j] == c1) || (
                j + 1 < board[i].length && board[i][j + 1] == c1);

    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }
        String value = "1";
        char[] values = value.toCharArray();
        int c = 0;
        char pre = values[0];
        String ans = "";
        while (n > 1) {
            ans = "";
            pre = values[0];
            c = 0;
            for (int i = 0; i < values.length; i++) {
                if (pre != values[i]) {
                    ans = ans + c + (pre - '0');
                    pre = values[i];
                    c = 1;
                } else {
                    c++;
                }
                if (i == values.length - 1) {
                    ans = ans + c + (pre - '0');
                }
            }
            values = ans.toCharArray();
            n--;
        }

        return ans;
    }
}
