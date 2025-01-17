package com.example.POO_Biblioteca.service;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.dao.BookDAO;
import com.example.POO_Biblioteca.dao.OrderDAO;
import com.example.POO_Biblioteca.dto.OrderDTO;
import com.example.POO_Biblioteca.dto.OrderItemDTO;
import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.model.Order;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderService {
    private final OrderDAO orderDAO = OrderDAO.getInstance();
    private final BookDAO bookDAO = BookDAO.getInstance();

    public Order registerOrder(OrderDTO dto) {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        try {
            final Array bookIds = conn.createArrayOf("INT", dto.getItems());
            final ArrayList<Book> books = bookDAO.getListByIds(conn, bookIds);

            if(books == null)
                return null;

            if(dto.getItems().length != books.size())
                return null;

            for(final OrderItemDTO item : dto.getItems()) {
                boolean finded = false;

                for(final Book book : books) {
                    if(book.getId() == item.getBookId()) {
                        finded = true;

                        if(item.getQuantity() > book.getQuantity())
                            return null;
                    }
                }

                if(!finded)
                    return null;
            }


        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }
}
