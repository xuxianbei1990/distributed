package calculation.chapter.likou.code1;

public class Code2Base1 {

    /**
     * 查询一个数组{1,2,4,2,3,5,7,1}.sum(array, L, R) 例如：查询该数组，第3到第5，第2到第4.
     * 方案1： 建一张2维表把数据要算的数据存下来  空间N*N 速度最快
     * 方案2：累加和；就是重新构建一个数组，每个下标代表：0-该下标的总和。空间N,速度差一个变量复制
     */
    public static int sum(int[] array, int l, int r) {
        //这个环节初始化一次
        int[] help = new int[array.length];
        help[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            help[i] = array[i] + help[i - 1];
        }
        //
        if (l > 0) {
            return help[r] - help[l - 1];
        } else {
            return help[r];
        }
    }

    //得到一个[0-X)左闭右开随机概率方法，Math.random();
    //得到一个[0-X)的x平方的概率的随机数，
    public static void random() {
        System.out.println(Math.max(Math.random(), Math.random()));
    }

    //随机返回1到5等概率的值
    public static int random5() {
        return (int) (Math.random() * 5) + 1;

    }

    //题目：由随机函数1-5得到随机函数1-7
    public static void randomCondition() {
        int i = 0;
        do {
            i = half() + half() << 1 + half() << 2;
        } while (i == 7);
        System.out.println(i + 1);
    }

    //50%概率
    private static int half() {
        int i = 0;
        int j = random5();
        while (true) {
            if (j < 3) {
                i = 0;
                break;
            } else if (j > 3) {
                i = 1;
                break;
            }
        }
        return i;
    }

    public static int unRandom() {
        return Math.random() < 0.84 ? 0 : 1;
    }

    //题目：由不等概率函数返回0,1 得到等概率1,0
    public static int x() {
        int i = 0;
        do {
            i = unRandom();
        } while (i == unRandom());
        return i;
    }
}
