package com.example.demo.model.dto.usersAndProductsDto;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class SoldProductsDto {
    @Expose
    private Integer count;
    @Expose
    private List<ProductsDetailsDto> soldProducts;

    public SoldProductsDto() {
        this.soldProducts = new ArrayList<>();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductsDetailsDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductsDetailsDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
