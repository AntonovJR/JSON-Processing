package com.example.demo.model.dto.ex1;

import com.google.gson.annotations.Expose;

import java.util.List;

public class OrderedCustomerDto {
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private Boolean isYoungDriver;
    @Expose
    private List<SaleCustomerDto> sales;

    public OrderedCustomerDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public List<SaleCustomerDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleCustomerDto> sales) {
        this.sales = sales;
    }
}
