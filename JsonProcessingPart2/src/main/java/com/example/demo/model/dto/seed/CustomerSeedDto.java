package com.example.demo.model.dto.seed;

import com.google.gson.annotations.Expose;


public class CustomerSeedDto {
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private Boolean isYoungDriver;

    public CustomerSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return birthDate;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.birthDate = dateOfBirth;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
