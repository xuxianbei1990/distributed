package Java8;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Name
 *
 * @author admin
 * Date 2019/3/28
 * VersionV1.0
 * @description jdk 8 项目实战
 */
public class Java8Project {
    public static void main(String[] args) {
//        testStreamObject();
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add("xxb");
        }

        List<String> listA = new ArrayList<>();
        //jdk 7
        for (String str : lists) {
            listA.add(str + 1);
        }
        System.out.println(ArrayUtils.toString(listA.toArray()));
        //jdk 8
        List<String> list8 = lists.stream().map(str -> str + 1).collect(Collectors.toList());
        System.out.println(ArrayUtils.toString(list8.toArray()));
    }

    private static void testStreamObject() {
        List<LimitItem> limitItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LimitItem limitItem = new LimitItem();
            limitItem.setId(i);
            limitItems.add(limitItem);
        }
        List<Integer> limitIds = new ArrayList<>();
        limitIds.add(1);
        limitIds.add(2);

        List<LimitItem> result = new ArrayList<>();
        List<LimitItem> result2;
        result2 = result;
        //上面是数据准备
        for (LimitItem limitItem : limitItems) {
            if (limitIds.contains(limitItem.getId())) {
                result.add(limitItem);
            }
        }
        // 证明了使用strem会重新创建对象
        System.out.println("jdk7" + ArrayUtils.toString(result.toArray()) + ":" + result.equals(result2));
        result.clear();
//        getLimitItems(limitItems, limitIds, result);
        result = limitItems.stream().filter(limitItem -> limitIds.contains(limitItem.getId())).collect(Collectors.toList());
        System.out.println("jdk8" + ArrayUtils.toString(result.toArray()) + ":" + result.equals(result2));

        List<LimitItem> result3 = new ArrayList<>();
        for (LimitItem limitItem : limitItems) {
            result3.add(limitItem);
        }
        System.out.println("jdk7" + result3.toString());
        result3.clear();
        limitItems.stream().forEach(result3::add);
        System.out.println("jdk8" + result3.toString());
    }

    private static void getLimitItems(List<LimitItem> limitItems, List<Integer> limitIds, List<LimitItem> result) {
        result = limitItems.stream().filter(limitItem -> limitIds.contains(limitItem.getId())).collect(Collectors.toList());
    }
}
