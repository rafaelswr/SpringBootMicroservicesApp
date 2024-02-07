package com.rafaelswr.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDTO {

    private Long id;
    //internal stock number
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;



}
