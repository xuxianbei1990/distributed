package calculation.chapter.likou;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2021/5/7
 * Time: 9:42
 * Version:V1.0
 * https://leetcode-cn.com/problems/binary-watch/
 * 二进制手表顶部有 4 个 LED 代表 小时（0-11），底部的 6 个 LED 代表 分钟（0-59）。
 * 每个 LED 代表一个 0 或 1，最低位在右侧。
 * 给定一个非负整数 n 代表当前 LED 亮着的数量，返回所有可能的时间。
 * 示例：
 * 输入: n = 1
 * 返回: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
 * 提示：
 * 输出的顺序没有要求。
 * 小时不会以零开头，比如 “01:00” 是不允许的，应为 “1:00”。
 * 分钟必须由两位数组成，可能会以零开头，比如 “10:2” 是无效的，应为 “10:02”。
 * 超过表示范围（小时 0-11，分钟 0-59）的数据将会被舍弃，也就是说不会出现 "13:00", "0:61" 等时间。
 * <p>
 * <p>
 * 分析过程：
 * 分别把小时和分钟定义出来。
 * 当是0时候：
 * 当是1时候：
 * 当是2时候：定义两个游标
 * 当是3时候：加一个跳标
 */
public class BinaryWatch {


    private static final int HOUR_LENGTH = 4;
    private static final int MINUTE_LENGTH = 6;

    private static final int[] TIME = {1 << 3, 1 << 2, 1 << 1, 1, 1 << 5, 1 << 4, 1 << 3, 1 << 2, 1 << 1, 1};

    public static List<String> readBinaryWatch(int turnedOn) {
        List<String> result = new ArrayList<>();
        if (turnedOn == 0) {
            result.add("0:00");
        }
        for (int i = 0; i < HOUR_LENGTH + MINUTE_LENGTH; i++) {

            switch (turnedOn) {
                case 1:
                    setvalue(result, i);
                    break;
                case 2: {
                    List<Integer> sonfixedInt = new ArrayList<>();
                    for (int j = i; sonfixedInt.size() < turnedOn; j++) {
                        sonfixedInt.add(j);
                    }
                    int j = i + turnedOn - 1;
                    while (j < HOUR_LENGTH + MINUTE_LENGTH) {
                        setvalue(result, sonfixedInt, j);
                        j++;
                    }
                   break;
                }
                default:{
                    List<List<Integer>> fixedInt = new ArrayList<>();
                    for (int skip = 0; skip < TIME.length - turnedOn; skip++) {
                        List<Integer> sonfixedInt = new ArrayList<>();
                        for (int j = i; fixedInt.size() < turnedOn; j++) {
                            sonfixedInt.add(j + skip);
                        }
                        fixedInt.add(sonfixedInt);
                    }
                    int j = i + turnedOn - 1;

                    while (j < HOUR_LENGTH + MINUTE_LENGTH) {
//                        setvalue(result, sonfixedInt, j);
                        j++;
                    }
                }
            }


        }
        return result;
    }

    private static void setvalue(List<String> result, int i) {
        int hour = 0;
        int minute = 0;
        if (i < HOUR_LENGTH) {
            hour = TIME[i] + hour;
        } else {
            minute += TIME[i] + minute;
        }
        String value = hour + ":" + String.format("%02d", minute);
        result.add(value);
    }

    private static void setvalue(List<String> result, List<Integer> fixInts, int j) {
        int hour = 0;
        int minute = 0;
        for (int i = 0; i < fixInts.size(); i++) {
            int temp = fixInts.get(i);
            if (temp < HOUR_LENGTH) {
                hour = TIME[temp] + hour;
            } else {
                minute += TIME[temp] + minute;
            }
        }
        if (j < HOUR_LENGTH) {
            hour = TIME[j] + hour;
        } else {
            minute = TIME[j] + minute;
        }
        if (minute >= 60) {
            return;
        }
        if (hour >= 12) {
            return;
        }
        String value = hour + ":" + String.format("%02d", minute);
        result.add(value);
    }

    /*
    ["0:03","0:05","0:06","0:09","0:10","0:12","0:17","0:18","0:20","0:24","0:33","0:34","0:36","0:40","0:48","1:01","1:02","1:04","1:08",
    "1:16","1:32","2:01","2:02","2:04","2:08","2:16","2:32","3:00","4:01","4:02","4:04","4:08","4:16","4:32","5:00",
    "6:00","8:01","8:02","8:04","8:08","8:16","8:32","9:00","10:00"]
     */
    public static void main(String[] args) {
        System.out.println(readBinaryWatch(3));
    }
}
