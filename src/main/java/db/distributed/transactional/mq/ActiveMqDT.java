package db.distributed.transactional.mq;

/**
 * Name activeMq 分布式事务
 *
 * @author xuxb
 * Date 2018-11-14
 * VersionV1.0
 * @description
 */
public interface ActiveMqDT {

    // 发送消息持久化
    boolean sendMessageToDuration();

    //执行本地业务
    boolean execLocal();

    // 改变消息状态为发送
    boolean changeMessageToSend();
}
