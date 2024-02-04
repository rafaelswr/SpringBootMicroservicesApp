package com.rafaelswr.backend_microservices.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelswr.backend_microservices.dto.ProductRequestDTO;
import com.rafaelswr.backend_microservices.repositories.ProductRepository;
import com.rafaelswr.backend_microservices.services.ProductService;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.print.attribute.standard.Media;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Delete all from test Database")
    void shouldDeleteData (){
        productRepository.deleteAll();
    }
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongo.uri",  () -> mongoDBContainer.getReplicaSetUrl("test"));
    }

    @Test
    @DisplayName("Shoud create an new instance of Product and add to test database")
    void createProduct() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/products/add")
                .content(objectMapper.writeValueAsString(
                        new ProductRequestDTO( "Iphone",
                                "Apple", BigDecimal.valueOf(2000)
                        )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());
    }


    @Test
    public void getAllProducts() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(3, productRepository.findAll().size());
    }

    @Test
    public void shouldEliminate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/{id}/delete",new ObjectId("65bfb3c3711207729c6384f1")))
                .andExpect(status().isAccepted());
    }



}