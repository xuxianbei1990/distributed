package calculation.chapter.ArrayCode;

import java.util.ArrayList;
import java.util.List;

public class WordFilter {

    static class Node {
        List<Integer> indexs;
        Node[] next;

        public Node() {
            this.indexs = new ArrayList();
            next = new Node[26];
        }
    }

    Node prev;
    Node tail;

    public WordFilter(String[] words) {
        prev = new Node();
        tail = new Node();
        for (int j = 0; j < words.length; j++) {
            char[] chars = words[j].toCharArray();
            Node prevTemp = prev;
            for (int i = 0; i < chars.length; i++) {
                if (prevTemp.next[chars[i] - 'a'] == null) {
                    prevTemp.next[chars[i] - 'a'] = new Node();
                    prevTemp = prevTemp.next[chars[i] - 'a'];
                } else {
                    prevTemp = prevTemp.next[chars[i] - 'a'];
                }
                prevTemp.indexs.add(j);
            }
            prevTemp = tail;
            for (int i = chars.length - 1; i >= 0; i--) {
                if (prevTemp.next[chars[i] - 'a'] == null) {
                    prevTemp.next[chars[i] - 'a'] = new Node();
                    prevTemp = prevTemp.next[chars[i] - 'a'];
                } else {
                    prevTemp = prevTemp.next[chars[i] - 'a'];
                }
                prevTemp.indexs.add(j);
            }
        }

    }

    public int f(String pref, String suff) {
        char[] prefs = pref.toCharArray();
        char[] suffs = suff.toCharArray();
        Node temp = prev;
        Node prefNode = search(prefs, temp);
        temp = tail;
        Node suffNode = search2(suffs, temp);
        if (suffNode != null && prefNode != null) {
            int ans = -1;
            List<Integer> small = prefNode.indexs.size() <= suffNode.indexs.size() ? prefNode.indexs : suffNode.indexs;
            List<Integer> big = small == prefNode.indexs ? suffNode.indexs : prefNode.indexs;
            for (int i = small.size() - 1; i >=0; i--) {
                ans = Math.max(search3(small.get(i), big), ans) ;
            }
            return ans;
        } else {
            return -1;
        }

    }

    private int search3(Integer index, List<Integer> indexs) {
        int l = 0;
        int r = indexs.size() - 1;
        while (l <= r) {
            int mid = l + r / 2;
            if (indexs.get(mid) > index) {
                r = mid - 1;
            } else if (indexs.get(mid) < index) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private Node search(char[] prefs, Node temp) {
        for (int i = 0; i < prefs.length; i++) {
            if (temp.next[prefs[i] - 'a'] == null) {
                return null;
            } else {
                temp = temp.next[prefs[i] - 'a'];
            }
        }
        return temp;
    }

    private Node search2(char[] prefs, Node temp) {
        for (int i = prefs.length - 1; i >= 0; i--) {
            if (temp.next[prefs[i] - 'a'] == null) {
                return null;
            } else {
                temp = temp.next[prefs[i] - 'a'];
            }
        }
        return temp;
    }

    public static void main(String[] args) {
        WordFilter wordFilter = new WordFilter(new String[]{"abbba", "abba"});
        wordFilter.f("ab", "ba");
    }
}
