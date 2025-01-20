package com.example.POO_Biblioteca.dao;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.dto.OrderDTO;
import com.example.POO_Biblioteca.dto.OrderItemDTO;
import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.model.Order;
import com.example.POO_Biblioteca.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "saleDate DATE NOT NULL," +
                "value DECIMAL(8, 2) NOT NULL" +
            ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS orderItem (" +
                "orderId INT NOT NULL," +
                "bookId INT NOT NULL," +
                "price DECIMAL(8, 2) NOT NULL," +
                "quantity INT NOT NULL," +
                "PRIMARY KEY (orderId, bookId)," +
                "FOREIGN KEY (orderId) REFERENCES orders(id)," +
                "FOREIGN KEY (bookId) REFERENCES book(id)" +
            ")");

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Order[] getAll(Connection conn) {
        final HashMap<Integer, Order> map = new HashMap<Integer, Order>();

        try {
            final Statement stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT *, b.quantity AS bookQtt, oi.quantity AS oiQtt FROM orders o INNER JOIN orderItem oi ON oi.orderId = o.id INNER JOIN book b ON b.id = oi.bookId");

            while (rs.next()) {
                Order order = map.get(rs.getInt("orderId"));

                if(order == null) {
                    ArrayList<OrderItem> items = new ArrayList<OrderItem>();
                    Book book = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getDouble("price"), rs.getInt("bookQtt"));
                    items.add(new OrderItem(book, rs.getInt("oiQtt"), rs.getDouble("price")));
                    map.put(rs.getInt("orderId"), new Order(rs.getInt("orderId"), items, rs.getDate("saleDate")));

                    continue;
                }

                Book book = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getDouble("price"), rs.getInt("bookQtt"));
                order.addItem(new OrderItem(book, rs.getInt("oiQtt"), rs.getDouble("price")));
            }

            return map.values().toArray(new Order[0]);
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public Order getById(Connection conn, int id) {
        try {
            final PreparedStatement stmt = conn.prepareStatement("SELECT *, b.quantity AS bookQtt, oi.quantity AS oiQtt FROM orders o INNER JOIN orderItem oi ON oi.orderId = o.id INNER JOIN book b ON b.id = oi.bookId WHERE o.id = ?");
            stmt.setInt(1, id);
            final ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                return null;

            Book book = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getDouble("price"), rs.getInt("bookQtt"));
            OrderItem aux = new OrderItem(book, rs.getInt("oiQtt"), rs.getDouble("price"));
            final Order order = new Order(rs.getInt("orderId"), rs.getDate("saleDate"));
            order.addItem(aux);

            while (rs.next()) {
                book = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getDouble("price"), rs.getInt("bookQtt"));
                order.addItem(new OrderItem(book, rs.getInt("oiQtt"), rs.getDouble("price")));
            }

            return order;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public Order create(Connection conn, double value, OrderItem[] items) {
        final String[] returnId = { "id" };
        final java.util.Date currentDate = new java.util.Date();
        try {
            final PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (value, saleDate) VALUES (?, ?)", returnId);
            stmt.setDouble(1, value);
            stmt.setDate(2, new Date(currentDate.getTime()));

            final int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0)
                return null;

            final ResultSet rs = stmt.getGeneratedKeys();
            if(!rs.next())
                return null;

            final int orderId = rs.getInt(1);
            stmt.close();

            final PreparedStatement stmtItems = conn.prepareStatement("INSERT INTO orderItem VALUES (?, ?, ?, ?)");

            for(int i = 0; i < items.length; i++) {
                stmtItems.setInt(1, orderId);
                stmtItems.setInt(2, items[i].getBook().getId());
                stmtItems.setDouble(3, items[i].getBook().getPrice());
                stmtItems.setInt(4, items[i].getQuantity());
                stmtItems.addBatch();
            }

            stmtItems.executeBatch();

            return new Order(orderId, new ArrayList<>(List.of(items)), currentDate);
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void deleteItems(Connection conn, int orderId) {
        try {
            final PreparedStatement stmt = conn.prepareStatement("DELETE FROM orderItem WHERE orderId = ?");
            stmt.setInt(1, orderId);

            stmt.executeUpdate();
            stmt.close();
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    public Order update(Connection conn, int id, double value, OrderItem[] items) {
        try {
            final PreparedStatement stmt = conn.prepareStatement("UPDATE orders SET value = ? WHERE id = ?");
            stmt.setDouble(1, value);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            stmt.close();

            final PreparedStatement stmtItems = conn.prepareStatement("INSERT INTO orderItem (orderId, bookId, price, quantity) VALUES (?, ?, ?, ?)");

            for(int i = 0; i < items.length; i++) {
                stmtItems.setInt(1, id);
                stmtItems.setInt(2, items[i].getBook().getId());
                stmtItems.setDouble(3, items[i].getBook().getPrice());
                stmtItems.setInt(4, items[i].getQuantity());
                stmtItems.addBatch();
            }

            stmtItems.executeBatch();
            stmtItems.close();

            return this.getById(conn, id);
        } catch(SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public boolean delete(Connection conn, int id) {
        try {
            final PreparedStatement stmtItems = conn.prepareStatement("DELETE FROM orderItem WHERE orderId = ?");
            final PreparedStatement stmtOrder = conn.prepareStatement("DELETE FROM orders WHERE id = ?");
            stmtItems.setInt(1, id);
            stmtOrder.setInt(1, id);

            stmtItems.executeUpdate();
            final int affectedRows = stmtOrder.executeUpdate();

            stmtItems.close();
            stmtOrder.close();

            return affectedRows > 0;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

}
