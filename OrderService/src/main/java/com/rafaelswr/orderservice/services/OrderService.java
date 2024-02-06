package com.rafaelswr.orderservice.services;

import com.rafaelswr.orderservice.dto.OrderLineItemsDTO;
import com.rafaelswr.orderservice.dto.OrderRequestDTO;
import com.rafaelswr.orderservice.models.Order;
import com.rafaelswr.orderservice.models.OrderLineItems;
import com.rafaelswr.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public void shouldCreateNewOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderItems = orderRequestDTO.getOrderLineItemsDTOList().stream().map(
                item->mapTo(item, order)
        ).toList();
        order.setOrderLineItemsList(orderItems);
        orderRepository.save(order);
    }

    private OrderLineItems mapTo(OrderLineItemsDTO item, Order order) {
        OrderLineItems shouldItem = new OrderLineItems(item.getSkuCode(), item.getPrice(), item.getQuantity());
        shouldItem.setOrder(order);
        return shouldItem;

    }
    public List<OrderLineItemsDTO> getAllItemsByOrderNumber(String orderNumber) throws Exception {
        Optional<Order> orderByNumber = orderRepository.getByOrderNumber(orderNumber);

        if(orderByNumber.isPresent()){
           return  orderByNumber.get().getOrderLineItemsList().stream().map(this::mapToItemDTO).collect(Collectors.toList());
        }else {
            throw new Exception("Not found order");
        }

    }

    private OrderLineItemsDTO mapToItemDTO(OrderLineItems orderLineItems) {
       return  OrderLineItemsDTO.builder()
                .id(orderLineItems.getId())
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .skuCode(orderLineItems.getSkuCode()).build();
    }

    public void deleteOrderByNumber(String orderNumber) throws Exception {
        Optional<Order> getOrder = orderRepository.getByOrderNumber(orderNumber);
        if (getOrder.isPresent()){
            orderRepository.delete(getOrder.get());
        }else{
            throw  new Exception("Not Found Order");
        }
    }
}
