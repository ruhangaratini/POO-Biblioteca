package com.example.POO_Biblioteca.service;

import com.example.POO_Biblioteca.config.Mysql;
import com.example.POO_Biblioteca.dao.BookDAO;
import com.example.POO_Biblioteca.dao.OrderDAO;
import com.example.POO_Biblioteca.dto.OrderDTO;
import com.example.POO_Biblioteca.dto.OrderItemDTO;
import com.example.POO_Biblioteca.dto.UpdateOrderDTO;
import com.example.POO_Biblioteca.model.Book;
import com.example.POO_Biblioteca.model.Order;
import com.example.POO_Biblioteca.model.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderDAO orderDAO = OrderDAO.getInstance();
    private final BookDAO bookDAO = BookDAO.getInstance();

    public Order[] getAll() {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        final Order[] orders = orderDAO.getAll(conn);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return orders;
    }

    public Order getById(int id) {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        final Order order = orderDAO.getById(conn, id);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return order;
    }

    public Order registerOrder(OrderDTO dto) {
        final Connection conn = Mysql.getConnection();
        double value = 0;

        if(conn == null)
            return null;

        try {
            conn.setAutoCommit(false);
            String bookIdList = Arrays.stream(dto.getItems())
                    .map(item -> String.valueOf(item.getBookId()))
                    .collect(Collectors.joining(","));

            final ArrayList<Book> books = bookDAO.getListByIds(conn, bookIdList);
            final ArrayList<OrderItem> items = new ArrayList<OrderItem>();

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

                        book.setQuantity(book.getQuantity() - item.getQuantity());
                        items.add(new OrderItem(book, item.getQuantity(), book.getPrice() * item.getQuantity()));
                        value += items.getLast().getValue();
                    }
                }

                if(!finded)
                    return null;
            }

            for(final Book book : books) {
                bookDAO.update(conn, book);
            }

            final Order order = orderDAO.create(conn, value, items.toArray(new OrderItem[0]));

            conn.commit();
            conn.close();
            return order;
        } catch (SQLException e) {
            System.out.println(e);

            try {
                conn.rollback();
                conn.close();
            } catch (Exception ex) {

            }
        }

        return null;
    }

    public Order update(UpdateOrderDTO dto) {
        final Connection conn = Mysql.getConnection();
        double value = 0;

        if(conn == null)
            return null;

        try {
            conn.setAutoCommit(false);
            this.deleteItems(conn, dto);
            String bookIdList = Arrays.stream(dto.getItems())
                    .map(item -> String.valueOf(item.getBookId()))
                    .collect(Collectors.joining(","));

            final ArrayList<Book> books = bookDAO.getListByIds(conn, bookIdList);
            final ArrayList<OrderItem> items = new ArrayList<OrderItem>();

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

                        book.setQuantity(book.getQuantity() - item.getQuantity());
                        items.add(new OrderItem(book, item.getQuantity(), book.getPrice() * item.getQuantity()));
                        value += items.getLast().getValue();
                    }
                }

                if(!finded)
                    return null;
            }

            for(final Book book : books) {
                bookDAO.update(conn, book);
            }

            final Order order = orderDAO.update(conn, dto.getId(), value, items.toArray(new OrderItem[0]));

            conn.commit();
            conn.close();
            return order;
        } catch (SQLException e) {
            System.out.println(e);

            try {
                conn.rollback();
                conn.close();
            } catch (Exception ex) {

            }
        }

        return null;
    }

    public void deleteItems(Connection conn, UpdateOrderDTO dto) {
        final Order order = orderDAO.getById(conn, dto.getId());

        for(OrderItem item : order.getItems()) {
            item.getBook().setQuantity(item.getBook().getQuantity() + item.getQuantity());
            bookDAO.update(conn, item.getBook());
        }

        orderDAO.deleteItems(conn, dto.getId());
    }

    public Order delete(int id) {
        final Connection conn = Mysql.getConnection();

        if(conn == null)
            return null;

        final Order order = orderDAO.getById(conn, id);

        if(order == null)
            return null;

        orderDAO.delete(conn, id);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return order;
    }
}
