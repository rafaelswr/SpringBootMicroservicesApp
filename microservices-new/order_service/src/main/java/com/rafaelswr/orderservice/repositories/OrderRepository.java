package com.rafaelswr.orderservice.repositories;

import com.rafaelswr.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
        Optional<Order> getByOrderNumber(String orderNumber);

}
