package com.rafaelswr.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDTO {

    private Long id;
    //internal stock number
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

}
