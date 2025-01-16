package com.example.POO_Biblioteca.service;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.dao.BookDAO;
import com.example.POO_Biblioteca.model.dto.BookDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookService {
    private final BookDAO repository = BookDAO.getInstance();

    public ArrayList<Book> getBooks() {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        final ArrayList<Book> books = repository.getAll(conn);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return books;
    }

    public Book getBookById(int id) {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        final Book book = repository.getById(conn, id);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return book;
    }

    public Book insertBook(BookDTO dto) {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        final Book book = repository.create(conn, dto);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return book;
    }

    public Book updateBook(Book book) {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        final Book bookUpdated = repository.update(conn, book);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return bookUpdated;
    }

    public Book deleteBook(int id) {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        final Book book = repository.getById(conn, id);
        final boolean deleted = repository.delete(conn, id);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return book;
    }

}
