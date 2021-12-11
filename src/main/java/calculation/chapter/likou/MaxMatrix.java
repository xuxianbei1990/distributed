package calculation.chapter.likou;


import java.util.Stack;

/**
 * 求最大子矩阵的大小
 * 给定一个整型矩阵map， 其中的值只有0, 1两种， 求其中全是1的所有矩阵区域中， 最大的矩形区域为1的数量
 *
 * @author: xuxianbei
 * Date: 2021/11/25
 * Time: 14:05
 * Version:V1.0
 */
public class MaxMatrix {

    public int maxRecSize(int[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        int[] height = new int[map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                height[j] = map[i][j] == 0 ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxRecFromBottom(height), maxArea);
        }
        return maxArea;
    }

    public int maxRecFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int maxArea = 0;
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int cureArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(maxArea, cureArea);
        }
        return maxArea;
    }

    public static void main(String[] args) {
        MaxMatrix maxMatrix = new MaxMatrix();
        int[][] map = new int[][]{{1, 1, 0, 0}, {0, 0, 1, 1}, {1, 1, 1, 1}, {0, 0, 1, 0}, {1, 1, 0, 0}};

        System.out.println(maxMatrix.maxRecSize(map));
    }

    public int execute(int[][] array) {

        return 0;
    }

}
