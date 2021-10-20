package calculation.chapter.base.datastructure.tree;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 前缀树以Map实现
 *
 * @author: xuxianbei
 * Date: 2021/10/19
 * Time: 18:44
 * Version:V1.0
 */
public class PrefixTreeMap {

    @Data
    static class NodeMap {
        private int pass;
        private int end;
        private Map<Integer, NodeMap> nextMaps;

        public NodeMap() {
            pass = 0;
            end = 0;
            nextMaps = new HashMap<>();
        }
    }

    private NodeMap root = new NodeMap();

    public void put(String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        NodeMap index = root;
        index.pass++;
        char[] chars = key.toCharArray();
        for (char aChar : chars) {
            int i = aChar;
            if (!index.nextMaps.containsKey(i)) {
                index.nextMaps.put(i, new NodeMap());
            }
            NodeMap nodeMap = index.nextMaps.get(i);
            nodeMap.pass++;
            index = nodeMap;
        }
        index.end++;
    }

    public int search(String key) {
        if (StringUtils.isEmpty(key)) {
            return -1;
        }
        NodeMap index = root;
        char[] chars = key.toCharArray();
        for (char aChar : chars) {
            int i = aChar;
            if (!index.nextMaps.containsKey(i)) {
                return -1;
            }
            index = index.nextMaps.get(i);
        }
        return index.pass;
    }

    public int size() {
        return root.pass;
    }

    public void delete(String key) {
        if (search(key) > 0) {
            NodeMap index = root;
            index.pass--;
            char[] chars = key.toCharArray();
            for (char aChar : chars) {
                int i = aChar;
                if (--index.nextMaps.get(i).pass == 0) {
                    index.nextMaps.remove(i);
                    return;
                }
                index = index.nextMaps.get(i);
            }
            index.end--;
        }
    }

    public static void main(String[] args) {
        PrefixTreeMap prefixTree26char = new PrefixTreeMap();
        prefixTree26char.put("中国abc");
        prefixTree26char.put("中美国abcd");
        prefixTree26char.put("中法国abcf");
        prefixTree26char.put("中德国abcz");
        prefixTree26char.put("中英国abcm");
        System.out.println(prefixTree26char.search("日本aba"));
        System.out.println(prefixTree26char.size());
        prefixTree26char.delete("中法国abcf");
        System.out.println(prefixTree26char.search("中"));
    }
}
