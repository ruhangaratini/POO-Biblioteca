package com.example.POO_Biblioteca.dao;

public class OrderDAO {
    private static OrderDAO instance;

    public static OrderDAO getInstance() {
        if(instance == null) {
            instance = new OrderDAO();
        }

        return instance;
    }

    private OrderDAO() {}


}
