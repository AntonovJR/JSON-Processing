package com.example.demo.model.dto.seed;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.util.Objects;

public class CategorySeedDto {
    @Expose
    @Size(min = 3, max = 15)
    private String name;

    public CategorySeedDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
