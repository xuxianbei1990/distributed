package calculation.chapter.base.datastructure.tree;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 题10
 * 派对的最大快乐值
 * 员工信息的定义如下：
 * class Employee{
 * //快乐值
 * public int happy;
 * //直接下级
 * List<Employee> subordinates;
 * }
 * 这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
 * 1.如果某个员工来了，那么这个员工的所有直接下级都不能来
 * 2.派对的整体快乐值是所有在场员工快乐值的累加
 * 3.你的目标是让派对的整体快乐值尽量大
 * 给定一棵多叉数的头结点boos，请返回派对的最大快乐值
 *
 * @author: xuxianbei
 * Date: 2021/11/29
 * Time: 15:04
 * Version:V1.0
 */
public class MultiTreeHappy {


    @Data
    static class Employee {
        private int happy;
        private List<Employee> subordinates;

        public Employee(int happy) {
            this.happy = happy;
            this.subordinates = new ArrayList<>();
        }
    }


    @Data
    static class Info {
        private Integer happy;
        private Integer sendHappySum;
        private Integer maxHappy;

        public Info(Integer happy, Integer sendHappySum, Integer maxHappy) {
            this.happy = happy;
            this.maxHappy = maxHappy;
            this.sendHappySum = sendHappySum;
        }
    }

    public Info maxHappy(Employee header, int index) {
        if (header == null) {
            return new Info(0, 0, 0);
        }
        List<Employee> employees = header.getSubordinates();
        Integer happy = header.happy;
        Integer sendHappySum = 0;
        if (!CollectionUtils.isEmpty(employees)) {
            Info info = maxHappy(employees.get(index), ++index);
            sendHappySum = sendHappySum + info.getHappy();
        } else {
            return new Info(happy, 0, happy);
        }

        Integer maxHappy = Math.max(happy, sendHappySum);

        return new Info(happy, sendHappySum, maxHappy);
    }

    static class Info2 {
        private int yes;
        private int no;

        public Info2(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public Info2 maxHappy2(Employee header) {
        if (header == null) {
            return new Info2(0, 0);
        }
        if (CollectionUtils.isEmpty(header.getSubordinates())) {
            return new Info2(header.happy, 0);
        }
        int yes = header.happy;
        int no = 0;
        for (Employee subordinate : header.getSubordinates()) {
            Info2 info2 = maxHappy2(subordinate);
            yes += info2.no;
            no += Math.max(info2.yes, info2.no);
        }
        return new Info2(yes, no);
    }


    public static void main(String[] args) {
        Employee employee = new Employee(300);
        Employee employee1 = new Employee(1);
        Employee employee2 = new Employee(200);
        employee.getSubordinates().add(employee1);
        employee.getSubordinates().add(employee2);

        MultiTreeHappy multiTreeHappy = new MultiTreeHappy();
        System.out.println(multiTreeHappy.maxHappy(employee, 0).getMaxHappy());
        Info2 info2 = multiTreeHappy.maxHappy2(employee);
        System.out.println(Math.max(info2.no, info2.yes));
    }
}
