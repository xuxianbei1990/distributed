package Java8;

import java.util.ArrayList;
import java.util.List;

/**
 * Name
 *
 * @author xxb
 * Date 2019/5/20
 * VersionV1.0
 * @description  结论：单线程下，增强型最快，stream.foreach次之 foreach 次之; 因为parallelStream是多线程实现，不在讨论范围之内
 * 所以代码最好优化为增强型。
 */
public class java8TestPerformance {


    private static void doSome() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> sourceList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            sourceList.add("第" + i + "条数据");
        }
        System.out.println("数据条数：" + sourceList.size());
        long a1 = System.currentTimeMillis();
        for (int i = 0; i < sourceList.size(); i++) doSome();
        long a2 = System.currentTimeMillis();
        System.out.println("普通for循环用时：" + (a2 - a1));
        long b1 = System.currentTimeMillis();
        for (String t : sourceList) doSome();
        long b2 = System.currentTimeMillis();
        System.out.println("增强for循环用时：" + (b2 - b1));
        long c1 = System.currentTimeMillis();
        sourceList.forEach((t) -> doSome());
        long c2 = System.currentTimeMillis();
        System.out.println("forEach循环用时：" + (c2 - c1));

        long e1 = System.currentTimeMillis();
        sourceList.stream().forEach((t) -> doSome());
        long e2 = System.currentTimeMillis();
        System.out.println("forEach-Stream循环用时：" + (e2 - e1));

        long d1 = System.currentTimeMillis();
        sourceList.parallelStream().forEach((t) -> doSome());
        long d2 = System.currentTimeMillis();
        System.out.println("forEach-parallelStream循环用时：" + (d2 - d1));
    }
}
