package com.example.POO_Biblioteca.dto;

import java.util.Date;

public class OrderDTO {
    private Date date;
    private OrderItemDTO[] items;

    public OrderDTO(Date date, OrderItemDTO[] items) {
        this.date = date;
        this.items = items;
    }

    public OrderDTO(OrderItemDTO[] items) {
        this.date = new Date();
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderItemDTO[] getItems() {
        return items;
    }

    public void setItems(OrderItemDTO[] items) {
        this.items = items;
    }
}
