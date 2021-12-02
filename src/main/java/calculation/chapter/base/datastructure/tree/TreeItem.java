package calculation.chapter.base.datastructure.tree;

import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeItem<?> treeItem = (TreeItem<?>) o;
        return data.equals(treeItem.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "TreeItem{" +
                "data=" + data +
                '}';
    }
}
