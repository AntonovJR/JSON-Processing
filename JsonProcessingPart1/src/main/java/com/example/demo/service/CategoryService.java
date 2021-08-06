package com.example.demo.service;

import com.example.demo.model.dto.productsByCategory.ProductsByCategoryDto;
import com.example.demo.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;
    Set<Category> findRandomCategories();

    List<ProductsByCategoryDto> productsDetailsByCategory();
}
