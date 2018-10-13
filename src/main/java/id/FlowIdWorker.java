package id;

import org.apache.logging.log4j.core.util.UuidUtil;

import java.util.UUID;

/**
 * 分布式id
 * https://mp.weixin.qq.com/s/9S88BRLZjJUWV8BV61oGSA
 * User: xuxb
 * Date: 2018-10-13
 * Time: 19:24
 * Version:V1.0
 */
public class FlowIdWorker {

    /**
     * JDK 自带
     * randomly: 基于随机数生成UUID，由于Java中的随机数是伪随机数，
     * 其重复的概率是可以被计算出来的。
     * 这个一般我们用下面的代码获取基于随机数的UUID:
     *
     * @return
     */
    public static void getJdkUUID() {
        System.out.println(UUID.randomUUID());
    }

    /*
     *UUID的优点:
     *通过本地生成，没有经过网络I/O，性能较快
     *无序，无法预测他的生成顺序。(当然这个也是他的缺点之一)
     *UUID的缺点:
     *128位二进制一般转换成36位的16进制，太长了只能用String存储，空间占用较多。
     *不能生成递增有序的数字
     *适用场景:UUID的适用场景可以为不担心过多的空间占用，以及不需要生成有递增趋势的数字。
     * 在Log4j里面他在UuidPatternConverter中加入了UUID来标识每一条日志。
     */
    public static void getlog4jUUID() {
        System.out.println(UuidUtil.getTimeBasedUuid().toString());
    }




    public static void main(String[] args) {
        getJdkUUID();
        getlog4jUUID();
        // 第三种 数据自增id
        // 4；redis id.
        // 5.zookeeper id 不推荐
        // 6. 数据库分段+服务缓存id
        // 7. 雪花算法 SnowFlake SnowflakeldWorker
    }
}
