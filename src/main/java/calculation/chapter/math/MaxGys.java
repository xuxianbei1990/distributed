package calculation.chapter.math;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 最大公约数
 *
 * @author: xuxianbei
 * Date: 2021/11/19
 * Time: 17:45
 * Version:V1.0
 */
public class MaxGys {

    @Data
    static class Student {
        private Integer num;
        private String color;

        public Student(Integer num, String color) {
            this.num = num;
            this.color = color;
        }
    }


    public static void main(String[] args) {
        Student student1 = new Student(100, "黄色");
        Student student2 = new Student(20, "金色");
        Student student3 = new Student(75, "水色");

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        System.out.println(maxCommonDivisor(students, (key) -> key.num));

    }

    public static <T> Integer maxCommonDivisor(List<T> list, Function<T, Integer> supplier) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        T indexObj = list.get(0);
        Integer index = supplier.apply(indexObj);
        for (T integer : list) {
            index = maxCommonDivisor(index, supplier.apply(integer));
        }
        return index;
    }

    public static int maxCommonDivisor(List<Integer> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        Integer index = list.get(0);
        for (Integer integer : list) {
            index = maxCommonDivisor(index, integer);
        }
        return index;
    }


    public static int maxCommonDivisor(int a, int b) {
        int i = a % b;
        int max_gcd;
        if (i == 0) {
            max_gcd = b;
        } else {
            a = b;
            b = i;
            max_gcd = maxCommonDivisor(a, b);
        }
        return max_gcd;
    }
}
