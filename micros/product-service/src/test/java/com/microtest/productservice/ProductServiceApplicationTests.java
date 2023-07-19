package com.microtest.productservice;

import com.microtest.productservice.dto.CreateProductRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer= new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepo;
	private ObjectMapper objectMapper= new ObjectMapper();



	/*@BeforeEach
	void setup() {
		ProductController productController= new ProductController(new ProductService(new P))
		this.mockMvc = MockMvcBuilders.standaloneSetup().build();
		this.objectMapper=new ObjectMapper();
	}
*/
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dpr){
		dpr.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void should_create_product() throws Exception {
		CreateProductRequest reqProduct=new CreateProductRequest("Table","nice", BigDecimal.valueOf(100));
		String jsonBody=objectMapper.writeValueAsString(reqProduct);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(APPLICATION_JSON)
				.content(jsonBody)
		).andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepo.findAll().size());
	}

}
