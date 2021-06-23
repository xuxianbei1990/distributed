package calculation.chapter.base;

/**
 * 计算工具
 * @author: xuxianbei
 * Date: 2021/6/21
 * Time: 9:44
 * Version:V1.0
 */
public class CalcUtils {


    /**
     * 产生随机数组，随机值
     * 正数
     * 根据Math.random  [0, 1)
     *
     * @param maxValue
     * @param maxSize
     * @return
     */
    public static int[] generateRandomArray(int maxValue, int maxSize) {
        int[] reslut = new int[(int) (Math.random() * maxSize + 1)];
        for (int i : reslut) {
            reslut[i] = (int) Math.random() * (maxValue + 1);
        }
        return reslut;
    }

}
