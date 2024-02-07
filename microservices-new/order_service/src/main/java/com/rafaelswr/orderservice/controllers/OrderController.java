package com.rafaelswr.orderservice.controllers;


import com.rafaelswr.orderservice.dto.OrderLineItemsDTO;
import com.rafaelswr.orderservice.dto.OrderRequestDTO;
import com.rafaelswr.orderservice.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        orderService.shouldCreateNewOrder(orderRequestDTO);
        log.info("New order executed...");
    }

    @GetMapping("/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderLineItemsDTO> getAll(@PathVariable String orderNumber) throws Exception {
        return orderService.getAllItemsByOrderNumber(orderNumber);
    }

    @DeleteMapping("/{orderNumber}/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteOrder (@PathVariable String orderNumber) throws Exception {
        orderService.deleteOrderByNumber(orderNumber);
        log.info("Order: "+orderNumber +" deleted\n");
    }

}
