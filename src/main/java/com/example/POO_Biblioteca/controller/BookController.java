package com.example.POO_Biblioteca.controller;

import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.model.ErrorResponse;
import com.example.POO_Biblioteca.model.dto.BookDTO;
import com.example.POO_Biblioteca.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@ResponseBody
@RequestMapping("/book")
public class BookController {
    private final BookService service = new BookService();

    @GetMapping
    public Book[] getBooks() {
        ArrayList<Book> books = service.getBooks();
        return books.toArray(new Book[0]);
    }

    @GetMapping("/{id}")
    public Object getBook(@PathVariable("id") int id) {
        final Book book = service.getBookById(id);

        if(book == null)
            return new ErrorResponse("Livro não encontrado");

        return book;
    }

    @PostMapping
    public Object createBook(@RequestBody BookDTO dto) {
        final Book book = service.insertBook(dto);

        if(book == null)
            return new ErrorResponse("Ocorreu um erro ao cadastrar livro");

        return book;
    }

    @PutMapping
    public Object updateBook(@RequestBody Book book) {
        System.out.println(book.getId());
        final Book bookUpdated = service.updateBook(book);

        if(bookUpdated == null)
            return new ErrorResponse("Ocorreu um erro ao atualizar livro");

        return bookUpdated;
    }

    @DeleteMapping("/{id}")
    public Object deleteBook(@PathVariable int id) {
        final Book book = service.deleteBook(id);

        if(book == null)
            return new ErrorResponse("Livro nao encontrado");

        return book;
    }

}
