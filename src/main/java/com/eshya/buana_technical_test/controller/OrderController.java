package com.eshya.buana_technical_test.controller;

import com.eshya.buana_technical_test.exception.ResourceNotFoundException;
import com.eshya.buana_technical_test.model.Order;
import com.eshya.buana_technical_test.model.ProductDetail;
import com.eshya.buana_technical_test.payload.MessageResponse;
import com.eshya.buana_technical_test.payload.order.OrderRequest;
import com.eshya.buana_technical_test.service.OrderService;
import com.eshya.buana_technical_test.service.ProductDetailService;
import com.eshya.buana_technical_test.utils.Constant;
import com.eshya.buana_technical_test.utils.JwtUtils;
import com.eshya.buana_technical_test.utils.TimeTraker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/" + Constant.API_NAME + "/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    private final ProductDetailService productService;

    private final JwtUtils jwtUtils;

    @Autowired
    public OrderController(OrderService orderService, ProductDetailService productService, JwtUtils jwtUtils) {
        this.orderService = orderService;
        this.productService = productService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Create new order
     * @param OrderRequest
     * @return Order
     */

    @TimeTraker
    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest request,
                             @RequestHeader("Authorization") String token) {


        String username = jwtUtils.getUsernameFromToken(token);
        logger.info("create order for product id : {} and quantity : {} by user : {}", request.getProductId(), request.getQuantity(), username);

        ProductDetail productDetail = productService.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found", "id", request.getProductId()));

        if(productDetail.getQuantity() - request.getQuantity() < 0) {
            return new ResponseEntity<>(new MessageResponse("Product quantity is not enough: " + productDetail.getQuantity() + "/" + request.getQuantity()), HttpStatus.BAD_REQUEST);
        }
        Order order = new Order();
        order.setProductDetail(productDetail);
        order.setQuantity(request.getQuantity());
        order.setOrderDate(LocalDateTime.now());
        order.setCustomerName(username);

        Order savedOrder = orderService.saveOrder(order);

        // update product quantity
        productDetail.setQuantity(productDetail.getQuantity() - request.getQuantity());
        productService.create(productDetail);

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    /**
     * Get order by id
     * @param id
     * @return Order
     */
    @TimeTraker
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") UUID id,
                              @RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUsernameFromToken(token);
        logger.info("get order by id : {} by user : {}", id, username);
        return orderService.findByCustomerNameAndId(username, id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found", "id", id));

    }

    /**
     * Get all order
     * @return List<Order>
     */

    @TimeTraker
    @GetMapping
    public List<Order> getAllOrder(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUsernameFromToken(token);
        logger.info("get all order by user : {}", username);
        return orderService.findAllByCustomerName(username);
    }

    /**
     * Delete order by id
     * @param id
     * @return ResponseEntity
     */
    @TimeTraker
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") UUID id,
                                        @RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUsernameFromToken(token);
        logger.info("delete order by id : {} by user : {}", id, username);
        Order order = orderService.findByCustomerNameAndId(username, id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found", "id", id));
        orderService.deleteOrder(order);
        return new ResponseEntity<>(new MessageResponse("Order deleted successfully"), HttpStatus.OK);
    }

}
