package com.rafaelswr.backend_microservices.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    private String name;
    private String description;
    private BigDecimal price;

    @Override
    public String toString() {
        return "ProductRequestDTO {" +
                "name ='" + name + '\'' +
                ", description ='" + description + '\'' +
                ", price =" + price +
                " }";
    }
}
