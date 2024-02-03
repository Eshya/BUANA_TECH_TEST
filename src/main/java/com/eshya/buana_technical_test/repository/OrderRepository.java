package com.eshya.buana_technical_test.repository;

import com.eshya.buana_technical_test.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByCustomerName(String customerName);

    Optional<Order> findByCustomerNameAndId(String customerName, UUID id);
}
