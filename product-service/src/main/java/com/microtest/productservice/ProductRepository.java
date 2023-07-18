package com.microtest.productservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.microtest.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
