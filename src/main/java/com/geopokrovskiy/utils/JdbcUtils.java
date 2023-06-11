package com.geopokrovskiy.utils;

import java.sql.*;

public class JdbcUtils {

    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(Constants.DB_URL, Constants.USERNAME, Constants.PASSWORD);
            } catch (Throwable e) {
                System.out.println("The connection cannot be established!");
                System.exit(1);
            }
        }
        return connection;
    }

    public static PreparedStatement preparedStatement(String sql) throws SQLException, ClassNotFoundException {
        return getConnection().prepareStatement(sql);
    }

    public static PreparedStatement preparedStatementWithKeys(String sql) throws SQLException, ClassNotFoundException {
        return getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }
}
