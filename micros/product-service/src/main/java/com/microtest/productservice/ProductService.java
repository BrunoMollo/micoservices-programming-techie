package com.microtest.productservice;

import com.microtest.productservice.dto.CreateProductRequest;
import com.microtest.productservice.dto.GetProductResponse;
import com.microtest.productservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepo;
    public void createProduct(CreateProductRequest dto){

        if(dto.getName()==null || dto.getName().length()==0){
            throw new IllegalStateException("name required");
        }
        if(dto.getPrice()==null){
            throw new IllegalStateException("price required");
        }




        Product product=Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();

        productRepo.save(product);
        log.info("Product is saved {}", product.getId());
    }

    public List<GetProductResponse> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(
                    p -> new GetProductResponse(p.getName(), p.getDescription(), p.getPrice())
                )
                .toList();
    }
}
