package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/mybase";
    public static final String DB_USER_NAME = "root";
    public static final String DB_USER_PASSWORD = "QWASzx12#";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_USER_PASSWORD);
            } catch (SQLException e) {

            }
        }
        return connection;
    }
}
