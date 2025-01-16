package com.example.POO_Biblioteca.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {

    static public Connection getConnection() {
        final MyEnvironment env = MyEnvironment.getInstance();

        try {
            return DriverManager.getConnection(env.get("DB_URL"), env.get("DB_USER"), env.get("DB_PASSWORD"));
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
