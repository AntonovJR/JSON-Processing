package com.example.demo.service;

import com.example.demo.model.dto.ex4.CarsWithPartsDto;
import com.example.demo.model.dto.ex2.CarsPrintDto;
import com.example.demo.model.entity.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {
    void seedCars() throws IOException;

    Car getRandomCar();

    List<CarsPrintDto> getCarByMake(String carBrand);

    List<CarsWithPartsDto> getCarsWithParts();
}
