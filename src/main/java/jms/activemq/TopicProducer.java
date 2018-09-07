package jms.activemq;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicProducer {
	AtomicInteger count = new AtomicInteger(0);

	ConnectionFactory connectionFactory;

	Connection connection;

	Session session;

	ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();

	public void init() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
					ActiveMQConnectionFactory.DEFAULT_PASSWORD, ActiveMQConnectionFactory.DEFAULT_BROKER_URL);

			connection = connectionFactory.createConnection();

			connection.start();

			session = connection.createSession(true, Session.SESSION_TRANSACTED);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String disname) {
		try {
			Queue queue = session.createQueue(disname);

			MessageProducer messageProducer = null;

			if (threadLocal.get() != null) {
				messageProducer = threadLocal.get();
			} else {
				messageProducer = session.createProducer(queue);
				threadLocal.set(messageProducer);
			}
			while (true) {
				Thread.sleep(1000);
				int num = count.getAndIncrement();

				TextMessage msg = session
						.createTextMessage(Thread.currentThread().getName() + "productor: 正在生产东西,count:" + num);
				System.out.println(Thread.currentThread().getName() + "productor:正在生产东西, count:" + num);

				// 发送消息
				messageProducer.send(msg);

				// 提交事务
				session.commit();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
