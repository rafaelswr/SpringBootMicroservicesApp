package com.rafaelswr.backend_microservices.controllers;

import com.rafaelswr.backend_microservices.dto.ProductRequestDTO;
import com.rafaelswr.backend_microservices.dto.ProductResponseDTO;
import com.rafaelswr.backend_microservices.models.Product;
import com.rafaelswr.backend_microservices.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    public ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequestDTO product){
        productService.createProduct(product);
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
    }

}
