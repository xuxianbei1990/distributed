package jms.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMqConsumer {

	public static void main(String[] args) throws JMSException {

		StringBuilder sb = new StringBuilder();
		sb.append(ActiveMQConnectionFactory.DEFAULT_USER).append(",").append(ActiveMQConnectionFactory.DEFAULT_PASSWORD)
				.append(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
		System.out.println(sb.toString());

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, ActiveMQConnectionFactory.DEFAULT_BROKER_URL);

		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

		/*
		 * PTP模式下是queue； 在pub/sub模式下是topic
		 */
		Destination destination = session.createQueue("queue1");

		/*
		 * 发送消息的生产者/接受消息的消费者
		 */
		MessageConsumer messageConsumer = session.createConsumer(destination);

		TextMessage mssage = (TextMessage) messageConsumer.receive();

		System.out.println(mssage.getText());

		if (connection != null) {
			connection.close();
		}
	}

}
