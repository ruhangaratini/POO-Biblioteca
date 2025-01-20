package com.example.POO_Biblioteca.dto;

public class OrderDTO {
    private OrderItemDTO[] items;

    public OrderDTO(OrderItemDTO[] items) {
        this.items = items;
    }

    public OrderItemDTO[] getItems() {
        return items;
    }

    public void setItems(OrderItemDTO[] items) {
        this.items = items;
    }
}
