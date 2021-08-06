package com.example.demo.model.dto.ex1;

import com.google.gson.annotations.Expose;

public class SaleCustomerDto {
    @Expose
    private CarSaleDto carSaleDto;
    @Expose
    private Integer discountPercentage;

    public SaleCustomerDto() {
    }

    public CarSaleDto getCarSaleDto() {
        return carSaleDto;
    }

    public void setCarSaleDto(CarSaleDto carSaleDto) {
        this.carSaleDto = carSaleDto;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
