package com.example.POO_Biblioteca.dao;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.model.dto.BookDTO;

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

    public ArrayList<Book> getAll(Connection conn) {
        final ArrayList<Book> books = new ArrayList<Book>();

        try {
            final Statement stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT * FROM book");

            while (rs.next()) {
                books.add(new Book(rs.getInt("id"), rs.getString("title")));
            }

            return books;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public Book getById(Connection conn, int id) {
        try {
            final PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE id = ?");
            stmt.setInt(1, id);

            final ResultSet rs = stmt.executeQuery();
            rs.next();

            final Book book = new Book(rs.getInt("id"), rs.getString("title"));
            stmt.close();

            return book;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public Book create(Connection conn, BookDTO dto) {
        final String[] returnId = { "id" };
        try {
            final PreparedStatement stmt = conn.prepareStatement("INSERT INTO book (title) VALUES (?)", returnId);
            stmt.setString(1, dto.getTitle());

            final int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0)
                return null;

            final ResultSet rs = stmt.getGeneratedKeys();
            if(!rs.next())
                return null;

            final Book book = new Book(rs.getInt(1), dto.getTitle());
            stmt.close();

            return book;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public Book update(Connection conn, Book book) {
        try {
            final PreparedStatement stmt = conn.prepareStatement("UPDATE book SET title = ? WHERE id = ?");
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getId());

            final int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0)
                return null;

            return book;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean delete(Connection conn, int id) {
        try {
            final PreparedStatement stmt = conn.prepareStatement("DELETE FROM book WHERE id = ?");
            stmt.setInt(1, id);

            final int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0)
                return false;

            stmt.close();

            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
