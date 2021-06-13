package Java8;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class LimitItem implements Serializable {
    private Integer id;

    private String name;

    private boolean limitItem;

//    private boolean operateItem;

    private static final long serialVersionUID = 1L;
}