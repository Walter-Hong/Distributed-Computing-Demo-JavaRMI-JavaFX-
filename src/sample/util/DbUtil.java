package sample.util;


import sample.config.AppConfig;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUtil {

    public static Connection connect = null;
    private String dbHost = AppConfig.dbHost;
    private String dbName = AppConfig.dbName;
    private String dbUser = AppConfig.dbUser;
    private String dbPass = AppConfig.dbPass;

    public static Connection getConnect() {
        if (connect == null) {
            DbUtil db = new DbUtil();
            db.connect();
        }
        return connect;
    }

    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DbUtil.connect = DriverManager.getConnection("jdbc:mysql://" + this.dbHost + "/" + this.dbName + "?" + "user=" + this.dbUser + "&password=" + this.dbPass);
            System.out.println("Database connected!");
        } catch (SQLException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void disconnect(Connection connect) {
        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(String method) {
        Connection conn = getConnect();
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(method);
        } catch (Exception e) {
            e.printStackTrace();
            disconnect(conn);
        }
    }

    public static Boolean execSql(String sql) {
        Connection conn = getConnect();
        Boolean result = null;
        try {
            result = conn.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            disconnect(conn);
        }
        return result;
    }

    public static ResultSet query(String sql) {
        Connection conn = getConnect();
        ResultSet resultSet = null;
        try {
            resultSet = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            disconnect(conn);
        }
        return resultSet;
    }

}
