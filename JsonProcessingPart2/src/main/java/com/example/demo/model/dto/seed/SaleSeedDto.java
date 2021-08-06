package com.example.demo.model.dto.seed;

import com.example.demo.model.entity.Car;
import com.example.demo.model.entity.Customer;
import com.google.gson.annotations.Expose;

public class SaleSeedDto {
    @Expose
    private Car car;
    @Expose
    private Customer customer;
    @Expose
    private Integer discountPercentage;

    public SaleSeedDto() {
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
