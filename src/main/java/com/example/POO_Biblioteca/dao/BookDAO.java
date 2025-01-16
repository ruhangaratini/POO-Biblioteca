package com.example.POO_Biblioteca.dao;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.model.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    private static BookDAO instance;

    public static BookDAO getInstance() {
        if(instance == null) {
            instance = new BookDAO();
        }

        return instance;
    }

    private BookDAO() {
        try {
            final Connection conn = Mysql.getConnection();

            if(conn == null) {
                return;
            }

            final Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS book (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "quantity INT NOT NULL DEFAULT 0" +
            ")");

            System.out.println(stmt.getResultSet());

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<Book> getAll(Connection connection) {
        final ArrayList<Book> books = new ArrayList<Book>();

        try {
            final Statement stmt = connection.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT * FROM book");

            while (rs.next()) {
                books.add(new Book(rs.getString("title")));
            }

            return books;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public Book getById(Connection connection, int id) {
        try {
            final PreparedStatement stmt = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
            stmt.setInt(1, id);

            System.out.println(stmt);

            final ResultSet rs = stmt.executeQuery();
            rs.next();

            final Book book = new Book(rs.getString("title"));
            stmt.close();

            return book;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
