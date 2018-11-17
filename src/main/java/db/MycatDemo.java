package db;

import lombok.Data;
import system.config.ServerConfig;

import java.sql.*;

/**
 * Name Mycat 数据库链接
 *
 * @author xuxb
 * Date 2018-11-12
 * VersionV1.0
 * @description
 */
public class MycatDemo {

    public static void main(String[] args) {
        Connection connection;
        PreparedStatement preparedStatement;
        String statement = "SELECT id, user_name userName FROM sample";
        try {
            try {
                Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
                DriverManager.registerDriver(driver);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            Test test = null;
            connection = DriverManager.getConnection("jdbc:mysql://" + ServerConfig.SERVER_MYCAT + "/college?user=" +
                    "test&password=123456&useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&" +
                    "zeroDateTimeBehavior=convertToNull&allowMultiQueries=true");
            preparedStatement = connection.prepareStatement(statement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                test = new Test();
                test.setId(rs.getInt(1));
                test.setUserName(rs.getString(2));
            }
            System.out.println(test.toString());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class Test {
        private int id;
        private String userName;

        @Override
        public String toString() {
            return "Test{" +
                    "id=" + id +
                    ", userName='" + userName + '\'' +
                    '}';
        }
    }
}
