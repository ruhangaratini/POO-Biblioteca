package com.example.POO_Biblioteca.service;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.repository.BookRepository;

import java.sql.Connection;
import java.util.ArrayList;

public class BookService {
    private final BookRepository repository = new BookRepository();

    public ArrayList<Book> getBooks() {
        final Connection conn = Mysql.getConnection();

        if(conn == null) {
            return null;
        }

        return repository.getAll(conn);
//        conn.close();
    }

}
