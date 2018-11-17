package db;

import lombok.Data;

import java.sql.*;

/**
 * Name 简单一个数据库连接
 *
 * @author xuxb
 * Date 2018-11-12
 * VersionV1.0
 * @description
 */
public class SimpleDBConnectionDemo {

    public static void main(String[] args) {
        Connection connection;
        PreparedStatement preparedStatement;
        String statement = "SELECT id FROM sample";
        try {
            try {
                Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
                DriverManager.registerDriver(driver);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            Test test = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/college?user=root&password=123456" +
                    "&useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&" +
                    "zeroDateTimeBehavior=convertToNull&allowMultiQueries=true");
            preparedStatement = connection.prepareStatement(statement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                test = new Test();
                test.setId(rs.getInt(1));
            }
            System.out.println(test.toString());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Data
    static class Test {
        private int id;


        @Override
        public String toString() {
            return "Test{" +
                    "id=" + id +
                    '}';
        }
    }
}
