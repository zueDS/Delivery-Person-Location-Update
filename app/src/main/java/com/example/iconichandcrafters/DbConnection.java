package com.example.iconichandcrafters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DbConnection {
    public static Connection c;
    public static String usernameloadingz;
    public static String emailzloadingz;
    public static String assigned_orderid;
    public static String completed_orderid;
    public static String assigned_buyeremailz;
    public static Connection createCon() throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        c = DriverManager.getConnection("jdbc:mysql://172.20.10.4:3307/finalhadcraft", "root", "123");

        return c;

    }

    public static void iud(String sql) throws Exception {

        if (c == null) {
            createCon();
        }
        c.createStatement().executeUpdate(sql);
    }

    public static ResultSet search(String sql) throws Exception {

        if (c == null) {
            createCon();
        }
        return c.createStatement().executeQuery(sql);
    }

}
