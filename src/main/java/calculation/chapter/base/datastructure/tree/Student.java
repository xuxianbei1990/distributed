package calculation.chapter.base.datastructure.tree;

import lombok.Data;

import java.util.Objects;

/**
 * @author: xuxianbei
 * Date: 2021/10/13
 * Time: 17:53
 * Version:V1.0
 */
@Data
public class Student {
    private int age;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
