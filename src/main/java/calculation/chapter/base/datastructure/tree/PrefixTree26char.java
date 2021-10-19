package calculation.chapter.base.datastructure.tree;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 假设只存26个小写字母的前缀树
 *
 * @author: xuxianbei
 * Date: 2021/10/16
 * Time: 16:30
 * Version:V1.0
 */
@Data
public class PrefixTree26char {

    @Data
    static class Node {
        private int pass;
        private int end;
        private Node[] nexts;

        public Node() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new Node[26];
        }
    }

    private Node root = new Node();

    public Node search(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        char[] chars = key.toCharArray();

        Node index = root;
        for (char aChar : chars) {
            int i = aChar - 'a';
            if (index.nexts[i] == null) {
                return null;
            }
            index = index.nexts[i];
        }
        return index;
    }

    public int put(String key) {
        if (StringUtils.isEmpty(key)) {
            return -1;
        }
        char[] chars = key.toCharArray();
        root.pass++;
        Node index = root;
        for (char aChar : chars) {
            int i = aChar - 'a';
            if (index.nexts[i] == null) {
                index.nexts[i] = new Node();
                index.nexts[i].pass++;
            } else {
                index.nexts[i].pass++;
            }
            index = index.nexts[i];
        }
        index.end++;
        return index.pass;
    }

    public int size() {
        return root.pass;
    }

    public int delete(String key) {
        if (search(key) != null) {
            char[] chars = key.toCharArray();
            root.pass--;
            Node index = root;
            for (char aChar : chars) {
                int i = aChar - 'a';
                if (--index.nexts[i].pass == 0) {
                    index.nexts[i] = null;
                    return 0;
                }
                index = index.nexts[i];
            }
            return index.end--;
        }
        return -1;
    }

    public static void main(String[] args) {
        PrefixTree26char prefixTree26char = new PrefixTree26char();
        prefixTree26char.put("abc");
        prefixTree26char.put("abcd");
        prefixTree26char.put("abcf");
        prefixTree26char.put("abcz");
        prefixTree26char.put("abcm");
        System.out.println(prefixTree26char.search("aba"));
        System.out.println(prefixTree26char.size());
        System.out.println(prefixTree26char.delete("abcf"));
        System.out.println(prefixTree26char.search("ab"));
    }

}
