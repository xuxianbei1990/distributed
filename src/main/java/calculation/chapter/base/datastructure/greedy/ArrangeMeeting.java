package calculation.chapter.base.datastructure.greedy;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 题3：
 * 给你一个会议数组，会议有开始时间，结束时间，一天安排最多会议，怎么实现
 *
 * @author: xuxianbei
 * Date: 2021/12/2
 * Time: 13:43
 * Version:V1.0
 */
public class ArrangeMeeting {

    @Data
    static class Meeting {
        private LocalDateTime begin;
        private LocalDateTime end;
    }

    public List<Meeting> execute(Meeting[] meetings, LocalDateTime begin, LocalDateTime end) {
        List<Meeting> origin = new ArrayList(Arrays.asList(meetings));
        origin.sort(Comparator.comparing(Meeting::getEnd));
        return origin;
    }
}
