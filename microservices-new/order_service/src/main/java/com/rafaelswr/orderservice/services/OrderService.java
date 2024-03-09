package com.rafaelswr.orderservice.services;

import com.rafaelswr.orderservice.dto.InventoryResponse;
import com.rafaelswr.orderservice.dto.OrderLineItemsDTO;
import com.rafaelswr.orderservice.dto.OrderRequestDTO;
import com.rafaelswr.orderservice.models.Order;
import com.rafaelswr.orderservice.models.OrderLineItems;
import com.rafaelswr.orderservice.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public synchronized void shouldCreateNewOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        log.info("ORDER REQUEST DTO:" + orderRequestDTO.toString());

        List<Mono<OrderLineItems>> orderLineItemsMonos = orderRequestDTO.getOrderLineItemsDTOList().stream()
                .map(item -> webClientBuilder.build().get()
                        .uri("http://inventory-service/inventory/ops",
                                uriBuilder ->
                                        uriBuilder.queryParam("skuCode", item.getSkuCode())
                                    .build())
                        .retrieve()
                        .bodyToMono(InventoryResponse.class)
                        .flatMap(inventory -> {
                            if (inventory.getIsInStock()) {
                                //transform Mono<InventoryResponse> to Mono<OrderLineItems> instance
                                return Mono.just(mapTo(item, order));
                            } else {
                                //item will not belong to flux
                             //   return Mono.empty();
                                return Mono.error(new Exception("NOT IN STOCK "));
                            }
                        }))
                .toList();

        Flux<OrderLineItems> orderLineItemsFlux = Flux.merge(orderLineItemsMonos);

        orderLineItemsFlux.collectList().subscribe(orderLineItems -> {
            //guardar nova order e associar a lista de orderlineItems
            order.getOrderLineItemsList().addAll(orderLineItems);
            orderRepository.save(order);

            //depois de salvar a ordem, decrementar a quantidade no inventário para cada produto;
            orderLineItems.forEach(item -> {
                webClientBuilder.build().put()
                        .uri("http://inventory-service/inventory/finalOps",
                                uriBuilder ->
                                        uriBuilder.queryParam("skuCode", item.getSkuCode())
                                                .queryParam("quantity", item.getQuantity())
                                                .build())
                        .retrieve()
                        .bodyToMono(Void.class)
                        .subscribe();
            });
        });
    }


    private OrderLineItems mapTo(OrderLineItemsDTO item, Order order) {
            double priceQuantity =  (item.getPrice().doubleValue() * item.getQuantity());
            OrderLineItems shouldItem = new OrderLineItems(item.getSkuCode(),BigDecimal.valueOf(priceQuantity), item.getQuantity());
            shouldItem.setOrder(order);
            return shouldItem;
    }

    public List<OrderLineItemsDTO> getAllItemsByOrderNumber(String orderNumber) throws Exception {
        Optional<Order> orderByNumber = orderRepository.getByOrderNumber(orderNumber);
        if(orderByNumber.isPresent()){
           return  orderByNumber.get().getOrderLineItemsList().stream().map(this::mapToItemDTO).toList();
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
