package com.example.demo.service;

import com.example.demo.model.dto.productsInRangeDto.ProductInRangeDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductInRangeDto> findAllProductsInRangeOrderByPrice(BigDecimal lowerBound, BigDecimal highBound);

}
