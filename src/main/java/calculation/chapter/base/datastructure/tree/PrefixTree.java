package calculation.chapter.base.datastructure.tree;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * 题9：前缀树
 *
 * @author: xuxianbei
 * Date: 2021/10/14
 * Time: 10:52
 * Version:V1.0
 */
@Data
public class PrefixTree {

    private Node root = new Node();

    @Data
    static class Node {
        private Node[] nextNodes;
        private int pass;
        private int end;
        private char value;

        public Node() {
            this.pass = 0;
            this.end = 0;
            this.value = 0;
            this.nextNodes = new Node[0];
        }

        public Node contain(char c) {
            for (Node nextNode : this.nextNodes) {
                if (nextNode.value == c) {
                    return nextNode;
                }
            }
            return null;
        }

        public void add(Node node) {
            int lastIndex = nextNodes.length;
            nextNodes = Arrays.copyOf(nextNodes, nextNodes.length + 1);
            nextNodes[lastIndex] = node;
        }
    }


    public void put(String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        root.pass++;
        char[] chars = value.toCharArray();
        Node inserN = root;
        for (int i = 0; i < chars.length; i++) {
            inserN = insert(chars[i], inserN, i == chars.length - 1);
        }


    }

    private Node insert(char c, Node node, boolean isLast) {
        Node indexNode = node.contain(c);
        if (indexNode != null) {
            //继续往下走
            nodeAdd(indexNode, isLast);
            return indexNode;
        } else {
            Node temp = new Node();
            temp.value = c;
            nodeAdd(temp, isLast);
            node.add(temp);
            return temp;
        }
    }

    private void nodeAdd(Node node, boolean isLast) {
        node.pass++;
        if (isLast) {
            node.end++;
        }
    }

}
