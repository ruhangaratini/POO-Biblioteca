package com.example.POO_Biblioteca.dao;

import com.example.POO_Biblioteca.config.Mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAO {
    private static OrderDAO instance;

    public static OrderDAO getInstance() {
        if(instance == null) {
            instance = new OrderDAO();
        }

        return instance;
    }

    private OrderDAO() {
        try {
            final Connection conn = Mysql.getConnection();

            if(conn == null) {
                return;
            }

            final Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS order (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "date DATETIME NOT NULL," +
                "value DECIMAL(8, 2) NOT NULL" +
            ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS orderItem (" +
                "orderId INT PRIMARY KEY," +
                "bookId INT PRIMARY KEY," +
                "price DECIMAL(8, 2) NOT NULL," +
                "quantity INT NOT NULL," +
                "FOREIGN KEY (orderId) REFERENCES order(id)," +
                "FOREIGN KEY (bookId) REFERENCES book(id)" +
            ")");

            System.out.println(stmt.getResultSet());

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


}
