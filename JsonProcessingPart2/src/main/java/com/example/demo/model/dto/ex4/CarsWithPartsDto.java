package com.example.demo.model.dto.ex4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarsWithPartsDto {
    @Expose
    private CarPartsDto car;
    @Expose
    private List<PartsPrintDto> parts;

    public CarsWithPartsDto() {
    }

    public CarPartsDto getCar() {
        return car;
    }

    public void setCar(CarPartsDto car) {
        this.car = car;
    }

    public List<PartsPrintDto> getParts() {
        return parts;
    }

    public void setParts(List<PartsPrintDto> parts) {
        this.parts = parts;
    }
}
