package com.example.POO_Biblioteca.model;

public class OrderItem {
    private Book book;
    private int quantity;
    private double value;

    public OrderItem(Book book, int quantity, double value) {
        this.book = book;
        this.quantity = quantity;
        this.value = value;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
