package com.rafaelswr.orderservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="t_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private String orderNumber;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "order")
    private List<OrderLineItems> orderLineItemsList = new ArrayList<>();



}
