package serializable;

import java.io.Serializable;

/**
 * User: xuxb
 * Date: 2018-09-26
 * Time: 10:50
 * Version:V1.0
 */
public class Teacher implements Serializable {
    private static final long serialVersionUID = 4644753740588166024L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                '}';
    }
}
