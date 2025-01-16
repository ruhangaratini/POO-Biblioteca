package com.example.POO_Biblioteca.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Order {
    private ArrayList<OrderItem> items;
    private Date date;

    public Order(ArrayList<OrderItem> items, Date date) {
        this.items = items;
        this.date = date;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
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
}
