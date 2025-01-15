package com.example.POO_Biblioteca.repository;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookRepository {
    private static BookRepository instance;

    public static BookRepository getInstance() {
        if(instance == null) {
            instance = new BookRepository();
        }

        return instance;
    }

    private BookRepository() {
        try {
            final Connection conn = Mysql.getConnection();
            final Statement stmt = conn.createStatement();
            stmt.executeQuery("CREATE TABLE IF NOT EXISTS book (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "quantity INT NOT NULL DEFAULT 0" +
            ")");

            conn.close();
        } catch (SQLException e) {

        }
    }

    public ArrayList<Book> getAll(Connection connection) {
        final ArrayList<Book> books = new ArrayList<Book>();

        try {
            final Statement stmt = connection.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT * FROM book");

            while (rs.next()) {
                books.add(new Book(rs.getString("name")));
            }

            return books;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
