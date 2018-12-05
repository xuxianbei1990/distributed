package db.distributed.transactional.mq;

import javax.jms.*;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-11-15
 * VersionV1.0
 * @description
 */
public class ActiveMqDTImpl implements ActiveMqDT {


    @Override
    public boolean sendMessageToDuration() {
        Connection connection = ActiveMqSessionFactory.getInstance().getSession();
        try {
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("distributed-transactional");
            // 创建持久性消息
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//            producer.send();
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean execLocal() {
        return false;
    }

    @Override
    public boolean changeMessageToSend() {
        return false;
    }
}
