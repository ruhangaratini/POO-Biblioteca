package com.example.POO_Biblioteca.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
    static final String url = "jdbc:mysql://localhost:3306/library";
    static final String user = "root";
    static final String password = "ifsp";

    static public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
