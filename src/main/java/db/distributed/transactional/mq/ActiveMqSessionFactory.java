package db.distributed.transactional.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import system.config.ServerConfig;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Name activeMq会话工厂
 *
 * @author xuxb
 * Date 2018-11-15
 * VersionV1.0
 * @description 1.缺点 无限连接数 connectionList 没有限制连接数
 */

public class ActiveMqSessionFactory {
    private ConnectionFactory factory;
    private Map<Connection, Boolean> connectionHashMap;
    private List<Connection> connectionList;
    private static ActiveMqSessionFactory singleton = new ActiveMqSessionFactory();

    public static ActiveMqSessionFactory getInstance() {
        return singleton;
    }

    private ActiveMqSessionFactory() {
        factory = new ActiveMQConnectionFactory(ServerConfig.ACTIVEMQADDRESS);

        connectionHashMap = new HashMap<>();
        connectionList = new ArrayList<>();
        newConnection();
    }

    public Connection getSession() {
        for (Connection item : connectionList) {
            if (!connectionHashMap.get(item)) {
                connectionHashMap.put(item, true);
                return item;
            }
        }
        return newConnection();
    }

    private Connection newConnection() {
        Connection connection = null;
        try {
            connection = factory.createConnection();
            // 3、开启连接
            connection.start();
            connectionHashMap.put(connection, false);
            connectionList.add(connection);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
