package calculation.chapter.base.datastructure.force;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 从左往右的尝试模型1
 * 规定1和A对应，2和B对应、3和C对应....,那么一个数字字符串比如"111"就可以转化为："AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果。
 *
 * @author: xuxianbei
 * Date: 2021/12/22
 * Time: 15:20
 * Version:V1.0
 */
public class CodeLeftToRight {

    static Map<String, String> help = new HashMap();

    static {
        int index = 1;
        for (int i = 65; i < 65 + 26; i++) {
            help.put(String.valueOf(index), String.valueOf((char) i));
            index++;
        }
    }

    @Data
    static class Info {
        private String key;
        private Integer index;

        public Info(String key, Integer index) {
            this.key = key;
            this.index = index;
        }
    }


    public void execute(String value) {
        char[] chars = value.toCharArray();
        LinkedList<Info> linkedList = new LinkedList<>();
        LinkedList<Info> helpList = new LinkedList<>();
        int index1 = 1;
        int index2 = 2;
        linkedList.add(new Info(help.get(String.valueOf(chars[0])), 0));
        linkedList.add(new Info(help.get(chars[0] + "" + chars[1]), 1));
        while (index1 < chars.length || index2 < chars.length) {
            Info base = new Info("", 0);
            if (!linkedList.isEmpty()) {
                base = linkedList.pop();
            } else {
                index1++;
                index2 += 2;
                linkedList.addAll(helpList);
                helpList.clear();
                continue;
            }
            boolean is = false;
            if (1 + base.getIndex() < chars.length) {
                String a = help.get(String.valueOf(chars[1 + base.getIndex()]));
                if (StringUtils.isEmpty(a)) {
                    a = "";
                }
                helpList.add(new Info(base.key + a, 1 + base.getIndex()));
                is = true;
            }
            if (2 + base.getIndex() < chars.length) {
                String b = help.get(chars[2 + base.getIndex() - 1] + "" + chars[2 + base.getIndex()]);
                if (StringUtils.isEmpty(b)) {
                    b = "";
                }
                helpList.add(new Info(base.key + b, 2 + base.getIndex()));
                is = true;
            }
            if (!is && base.key != "") {
                helpList.add(base);
            }
        }
        System.out.println(linkedList);
    }

    public static void main(String[] args) {
        CodeLeftToRight codeLeftToRight = new CodeLeftToRight();
        codeLeftToRight.execute("112197");
    }

}
