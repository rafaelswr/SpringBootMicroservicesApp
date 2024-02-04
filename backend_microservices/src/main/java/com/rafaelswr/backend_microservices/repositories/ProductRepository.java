package com.rafaelswr.backend_microservices.repositories;

import com.rafaelswr.backend_microservices.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
    Optional<Product> findByProductId(String productId);
}
