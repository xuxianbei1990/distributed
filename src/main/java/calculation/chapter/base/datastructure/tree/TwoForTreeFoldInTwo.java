package calculation.chapter.base.datastructure.tree;

/**
 * 题6：
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上对折1次，压出折痕后展开，此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。如果从纸条的下边向上连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕，下折痕和上折痕。
 * 给定一个输入参数N，代表纸条都从下边向上连续对折N次。请从上到下打印所有的折痕的方向。
 * 例如 N =1时， 打印 down；N=2时，打印 down down up;
 *
 * @author: xuxianbei
 * Date: 2021/11/16
 * Time: 13:33
 * Version:V1.0
 */
public class TwoForTreeFoldInTwo {

    public void execute(boolean isConcave, int n, int level) {
        if (n < level) {
            return;
        }
        execute(true, n, ++level);
        System.out.println(isConcave ? "凹" : "凸");
        execute(false, n, level);
    }

    public static void main(String[] args) {
        TwoForTreeFoldInTwo twoForTreeFoldInTwo = new TwoForTreeFoldInTwo();
        twoForTreeFoldInTwo.execute(true, 1, 1);
    }
}
