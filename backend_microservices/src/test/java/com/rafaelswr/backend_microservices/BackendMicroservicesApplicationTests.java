package com.rafaelswr.backend_microservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelswr.backend_microservices.dto.ProductRequestDTO;
import com.rafaelswr.backend_microservices.dto.ProductResponseDTO;
import com.rafaelswr.backend_microservices.models.Product;
import com.rafaelswr.backend_microservices.repositories.ProductRepository;
import com.rafaelswr.backend_microservices.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@Testcontainers
//@AutoConfigureMockMvc
class BackendMicroservicesApplicationTests {
	@Test
	void contextLoads() {
	}
/**
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry){
		dymDynamicPropertyRegistry.add("spring.data.mongo.uri", mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequestDTO productRequestDTO = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequestDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString)
		).andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}
	private ProductRequestDTO getProductRequest() {
		return ProductRequestDTO.builder()
				.name("Iphone 13")
				.description("Iphone 13 2023")
				.price(BigDecimal.valueOf(1200))
				.build();
	}
	@Test
	void shouldGetProducts() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products")
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		Mockito.when(productService.getAllProducts());
	}
**/



}
