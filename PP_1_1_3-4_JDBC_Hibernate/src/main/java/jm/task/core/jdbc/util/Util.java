package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TestUser";
    private static final String USER = "root";
    private static final String PASS = "26042004Marina.";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    public static Connection getConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        return connection;
    }
}
