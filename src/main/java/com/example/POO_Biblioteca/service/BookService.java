package com.example.POO_Biblioteca.service;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.dao.BookDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookService {
    private final BookDAO repository = BookDAO.getInstance();

    public ArrayList<Book> getBooks() {
        final Connection conn = Mysql.getConnection();

        if(conn == null) {
            return null;
        }

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

        System.out.println(id);

        if(conn == null) {
            return null;
        }

        final Book book = repository.getById(conn, id);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return book;
    }

}
