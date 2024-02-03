package com.eshya.buana_technical_test.payload.order;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderRequest {
    private UUID productId;
    private int quantity;
}
