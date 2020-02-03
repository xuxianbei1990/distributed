package cache.Bloom;

import java.io.*;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Name 布隆过滤器
 *
 * @author xuxb
 * Date 2018-11-01
 * VersionV1.0
 * @description 最大可以存储  Integer.MAX_VALUE 信息
 * 可以用来过滤信息
 * 场景及其应用
 * https://www.cnblogs.com/xujian2014/p/5491286.html
 */
public class BloomFilter {

    // 已经使用的数量
    private AtomicInteger usedCount = new AtomicInteger(0);
    private final Double autoClearRate;
    // 并发测试例子 http://huangyunbin.iteye.com/blog/2194731?utm_source=tuicool
    private BitSet bitSet;
    private final int size;
    private final int[] seeds = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53};

    /**
     * @param count         预期处理的数据规模，如预期用于处理1百万数据的查重，这里则填写1000000
     * @param autoClearRate 自动清空过滤器内部信息的使用比率，传null则表示不会自动清理，
     *                      当过滤器使用率达到100%时，则无论传入什么数据，都会认为在数据已经存在了
     *                      当希望过滤器使用率达到80%时自动清空重新使用，则传入0.8
     */
    public BloomFilter(int count, Double autoClearRate) {
        this.size = count;
        bitSet = new BitSet();
        this.autoClearRate = autoClearRate;
    }

    public double getUsedRate() {
        return (double) usedCount.intValue() / (double) size;
    }

    /**
     * 如果不存在则添加，否则不做任何处理
     *
     * @param data
     * @return true: 设置成功， false设置失败
     */
    public boolean setNX(String data) {
        checkNeedClear();
        boolean result = false;
        int hashValue;
        for (int seed : seeds) {
            hashValue = hash(data, seed);
            synchronized (bitSet) {
                if (!bitSet.get(hashValue)) {
                    bitSet.set(hashValue);
                    result = true;
                }
            }
        }
        if (result) {
            usedCount.incrementAndGet();
        }
        return result;
    }

    // 清空
    private void checkNeedClear() {
        if (autoClearRate != null) {
            if (getUsedRate() >= autoClearRate) {
                synchronized (this) {
                    if (getUsedRate() >= autoClearRate) {
                        bitSet.clear();
                        usedCount.set(0);
                    }
                }
            }
        }
    }

    public void saveFilterToFile(String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 清空过滤器中的记录信息
     */
    public void clear() {
        usedCount.set(0);
        bitSet.clear();
    }

    public static BloomFilter readFilterFromFile(String path) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            return (BloomFilter) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 也说不出那种hash算法好
    private int hash2(String data, int seed) {
        int hash = 0;
        for (int i = 0; i < data.length(); i++) {
            hash = i * hash + data.charAt(i);
        }
        hash = hash * seed % size;
        return Math.abs(hash);
    }

    // 也说不出那种hash算法好 不过以下是hashMap采用的算法。
    private int hash(String data, int seed) {
        int hash = 0;
        for (int index = 0; index < data.length(); index++) {
            hash = hash * seed + data.charAt(index);
        }
        return (size - 1) & hash;
    }

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < 3; i++) {
            bitSet.set(i);
        }
        System.out.print(bitSet.size());


//        BloomFilter fileter = new BloomFilter(10000, null);
//        System.out.println(fileter.setNX("1111111111111"));
//        System.out.println(fileter.setNX("2222222222222222"));
//        System.out.println(fileter.setNX("3333333333333333"));
//        System.out.println(fileter.setNX("444444444444444"));
//        System.out.println(fileter.setNX("5555555555555"));
//        System.out.println(fileter.setNX("6666666666666"));
//        System.out.println(fileter.setNX("1111111111111"));
    }
}
