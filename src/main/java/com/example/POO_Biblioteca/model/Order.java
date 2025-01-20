package com.example.POO_Biblioteca.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int id;
    private ArrayList<OrderItem> items;
    private Date date;

    public Order(int id, ArrayList<OrderItem> items, Date date) {
        this.id = id;
        this.items = items;
        this.date = date;
    }

    public Order(int id, Date date) {
        this.id = id;
        this.items = new ArrayList<OrderItem>();
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderItem[] getItems() {
        return items.toArray(new OrderItem[0]);
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }
}
