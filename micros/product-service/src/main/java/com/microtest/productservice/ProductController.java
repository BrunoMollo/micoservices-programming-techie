package com.microtest.productservice;

import com.microtest.productservice.dto.CreateProductRequest;
import com.microtest.productservice.dto.GetProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController @RequestMapping("api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping @ResponseStatus(OK)
    public List<GetProductResponse> get_all_products(){
        return productService.getAllProducts();
    }

    @PostMapping @ResponseStatus(CREATED)
    public void create_product(@RequestBody CreateProductRequest dto){
        productService.createProduct(dto);
    }




}
