package com.example.POO_Biblioteca.dto;

public class UpdateOrderDTO {
    private int id;
    private OrderItemDTO[] items;

    public UpdateOrderDTO(int id, OrderItemDTO[] items) {
        this.id = id;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderItemDTO[] getItems() {
        return items;
    }

    public void setItems(OrderItemDTO[] items) {
        this.items = items;
    }
}
