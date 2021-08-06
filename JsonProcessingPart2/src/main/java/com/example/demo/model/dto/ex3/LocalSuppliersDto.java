package com.example.demo.model.dto.ex3;

import com.google.gson.annotations.Expose;

public class LocalSuppliersDto {
    @Expose
    private Long Id;
    @Expose
    private String name;
    @Expose
    private Integer partsCount;

    public LocalSuppliersDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }
}
