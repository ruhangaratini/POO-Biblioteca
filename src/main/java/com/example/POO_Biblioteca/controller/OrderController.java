package com.example.POO_Biblioteca.controller;

import com.example.POO_Biblioteca.dto.OrderDTO;
import com.example.POO_Biblioteca.dto.UpdateOrderDTO;
import com.example.POO_Biblioteca.model.ErrorResponse;
import com.example.POO_Biblioteca.model.Order;
import com.example.POO_Biblioteca.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@ResponseBody
@RequestMapping("/order")
public class OrderController {
    private OrderService service = new OrderService();

    @GetMapping
    public Object[] getOrders() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Object getOrderById(@PathVariable int id) {
        final Order order = service.getById(id);

        if(order == null)
            return new ErrorResponse("Pedido não encontrado");

        return order;
    }

    @PostMapping
    public Object registerOrder(@RequestBody OrderDTO dto) {
        final Order order = this.service.registerOrder(dto);

        if(order == null)
            return new ErrorResponse("Ocorreu um erro ao cadastrar pedido, verifique os livros e os estoques");

        return order;
    }

    @PutMapping
    public Object updateOrder(@RequestBody UpdateOrderDTO dto) {
        final Order order = this.service.update(dto);

        if(order == null)
            return new ErrorResponse("Verifique se o pedido é válido e as quantidades em estoque");

        return order;
    }

    @DeleteMapping("/{id}")
    public Object deleteOrder(@PathVariable int id) {
        final Order order = service.delete(id);

        if(order == null)
            return new ErrorResponse("Pedido não encontrado");

        return order;
    }
}
