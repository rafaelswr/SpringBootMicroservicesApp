package com.rafaelswr.backend_microservices.services;

import com.rafaelswr.backend_microservices.dto.ProductRequestDTO;
import com.rafaelswr.backend_microservices.dto.ProductResponseDTO;
import com.rafaelswr.backend_microservices.models.Product;
import com.rafaelswr.backend_microservices.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    public ProductRepository productRepository;

    private MongoTemplate mongoTemplate;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequestDTO product) {
        try{
            Product buildProduct = new Product(product.getName(), product.getDescription(), product.getPrice());
            productRepository.save(buildProduct);

            log.info("[Product] {}: added", buildProduct.getName());

        }catch (RepositoryCreationException e){
            System.out.println("error on creating product "+e);
        }
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    private ProductResponseDTO mapToProductResponse(Product product) {
        return ProductResponseDTO.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
        .build();
    }

    public void deleteProduct(String id) {
        Optional<Product> p = Optional.ofNullable(mongoTemplate.findById(id, Product.class));
        p.ifPresentOrElse(
                pr -> productRepository.delete(pr),
                () -> log.info("Product not found...")
        );

    }
}

