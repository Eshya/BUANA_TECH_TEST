package com.eshya.buana_technical_test.payload.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDetailRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

}
