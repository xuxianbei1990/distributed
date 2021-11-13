package calculation.chapter.base.datastructure.tree;

import lombok.Data;

/**
 * @author: xuxianbei
 * Date: 2021/11/9
 * Time: 13:44
 * Version:V1.0
 */
@Data
public class TreeItem<T> {

    private TreeItem<T> left;
    private TreeItem<T> right;
    private T data;

    public TreeItem(T data) {
        this.data = data;
    }
}
