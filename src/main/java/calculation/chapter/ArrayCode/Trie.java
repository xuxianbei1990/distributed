package calculation.chapter.ArrayCode;

import java.util.HashMap;
import java.util.Map;

public class Trie {
    static class Node {
        int pass;
        int key;
        Map<Integer, Node> nexts;

        public Node(int key) {
            this.key = key;
            pass = 1;
            nexts = new HashMap<>();
        }
    }

    public static int[] sameTeamsArray(int[][] bs, int[][] as) {
        Node root = new Node(0);
        Node temp;
        for (int[] ints : as) {
            temp = root;
            for (int i = 1; i < ints.length; i++) {
                if (!temp.nexts.containsKey(ints[i] - ints[i - 1])) {
                    temp.nexts.put(ints[i] - ints[i - 1], new Node(ints[i] - ints[i - 1]));
                } else {
                    temp.nexts.get(ints[i] - ints[i - 1]).pass++;
                }
                temp = temp.nexts.get(ints[i] - ints[i - 1]);
            }
        }
        int[] result = new int[bs.length];
        for(int j = 0; j< bs.length; j++){
            int[] ints = bs[j];
            temp = root;
            for (int i = 1; i < ints.length; i++) {
                if (!temp.nexts.containsKey(ints[i] - ints[i - 1])) {
                    result[j] = 0;
                    break;
                }
                temp = temp.nexts.get(ints[i] - ints[i - 1]);
            }
            result[j] = temp.pass;
        }
        return result;
    }


}
