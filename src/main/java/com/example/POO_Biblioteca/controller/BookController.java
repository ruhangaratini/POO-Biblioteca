package com.example.POO_Biblioteca.controller;

import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@ResponseBody
@RequestMapping("/book")
public class BookController {
    private final BookService service = new BookService();

    @GetMapping
    public Object[] getBooks() {
        ArrayList<Book> books = service.getBooks();
        return books.toArray();
    }

}
