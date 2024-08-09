package calculation.chapter.ArrayCode;

import java.util.HashMap;
import java.util.Map;

public class RandomizedSet {

    Map<Integer, Integer> keyMap;
    Map<Integer, Integer> indexMap;
    int size;

    public RandomizedSet() {
        keyMap = new HashMap();
        indexMap = new HashMap<>();
        size = 0;
    }

    public boolean insert(int val) {
        if (!keyMap.containsKey(val)) {
            keyMap.put(val, size);
            indexMap.put(size, val);
            size++;
            return true;
        }
        return false;
    }

    public boolean remove(int val) {
        if (keyMap.containsKey(val)) {
            size--;
            int value = indexMap.get(size);
            int index = keyMap.get(val);
            keyMap.put(value, index);
            indexMap.put(index, value);
            keyMap.remove(val);
            indexMap.remove(size);
            return true;
        }
        return false;
    }

    public int getRandom() {
       return indexMap.get((int)(Math.random() *  size));
    }

    public static void main(String[] args) {
        RandomizedSet set = new RandomizedSet();


        System.out.println(set.findNumberOfLIS1(new int[]{1,3,5,4,7}));

    }

    // 好理解的方法，时间复杂度O(N^2)
    public static int findNumberOfLIS1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] lens = new int[n];
        int[] cnts = new int[n];
        lens[0] = 1;
        cnts[0] = 1;
        int maxLen = 1;
        int allCnt = 1;
        for (int i = 1; i < n; i++) {
            int preLen = 0;
            int preCnt = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] >= nums[i] || preLen > lens[j]) {
                    continue;
                }
                if (preLen < lens[j]) {
                    preLen = lens[j];
                    preCnt = cnts[j];
                } else {
                    preCnt += cnts[j];
                }
            }
            lens[i] = preLen + 1;
            cnts[i] = preCnt;
            if (maxLen < lens[i]) {
                maxLen = lens[i];
                allCnt = cnts[i];
            } else if (maxLen == lens[i]) {
                allCnt += cnts[i];
            }
        }
        return allCnt;
    }
}
