package com.example.demo.model.dto.seed;

import com.example.demo.model.entity.PartSupplier;
import com.google.gson.annotations.Expose;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;

public class PartsSeedDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private Integer quantity;

    public PartsSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
