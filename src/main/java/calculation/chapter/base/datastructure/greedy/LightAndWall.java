package calculation.chapter.base.datastructure.greedy;

import org.springframework.util.StringUtils;

/**
 * 题4：
 * 给定一个字符串str，只由X和.两种字符构成。X表示墙，不能放灯，也不需要点亮，.表示居名点，可以放灯，需要点亮。
 * 如果灯放在i位置，可以让i-1, i, i+1三个位置点亮，返回如果点亮str中所有需要点亮的位置，至少需要几盏灯。
 *
 * @author: xuxianbei
 * Date: 2021/12/2
 * Time: 13:51
 * Version:V1.0
 */
public class LightAndWall {

    public Integer execute(String lightWall) {
        if (StringUtils.isEmpty(lightWall)) {
            return 0;
        }
        char[] chars = lightWall.toCharArray();
        int result = 0;
        for (int i = 0; i < chars.length; ) {
            if (chars[i] == '.') {
                if (chars[i + 1] == '.') {
                    if (chars[i + 2] == '.') {
                        result++;
                        i += 2;
                    } else {
                        result++;
                        i++;
                    }
                } else {
                    result++;
                    i++;
                }
            } else {
                i++;
            }
        }
        return result;
    }
}
