package calculation.chapter.likou.code1;

/**
 * 位运算
 */
public class Code1 {


    public static void main(String[] args) {
//        printInt(-1);

        intPlus(4);
        intPlusEx(4);
    }

    /**
     * 4: 00000000000000000000000000000100
     * -1 11111111111111111111111111111111  计算方式 值取反 + 1
     *
     * @param num
     */
    private static void printInt(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
    }

    /**
     * 计算 1!+2!+3!+.....N!;怎么算
     * N!= 1*1*2*3*....N
     *
     * @param num
     */
    public static void intPlus(int num) {
        int sum = 0;
        for (int i = 1; i <= num; i++) {
            sum += intPlus1(i);
        }
        System.out.println(sum);
    }

    private static int intPlus1(int num) {
        int sum = 1;
        for (int i = 1; i <= num; i++) {
            sum *= i;
        }
        return sum;
    }

    private static int intPlus2(int init, int num) {
        return init * num;
    }

    public static void intPlusEx(int num) {
        int son = 1;
        int sum = 0;
        for (int i = 1; i <= num; i++) {
            son = son * i;
            sum += son;
        }
        System.out.println(sum);
    }
}
