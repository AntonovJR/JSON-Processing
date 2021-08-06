package com.example.demo.service.impl;

import com.example.demo.model.dto.ex4.CarPartsDto;
import com.example.demo.model.dto.ex4.CarsWithPartsDto;
import com.example.demo.model.dto.ex4.PartsPrintDto;
import com.example.demo.model.dto.ex2.CarsPrintDto;
import com.example.demo.model.dto.seed.CarSeedDto;
import com.example.demo.model.entity.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;
import com.example.demo.service.PartService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String CARS_FILE_NAME = "cars.json";
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final PartService partService;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.partService = partService;
    }

    @Override
    public void seedCars() throws IOException {
        if (this.carRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(RESOURCE_FILE_PATH + CARS_FILE_NAME));
        CarSeedDto[] carSeedDtos = this.gson.fromJson(fileContent, CarSeedDto[].class);

        Arrays.stream(carSeedDtos)
                .map(carSeedDto -> {
                    Car car = this.modelMapper.map(carSeedDto, Car.class);
                    car.setPartList(this.partService.getRandomPartsList());
                    return car;
                })
                .forEach(this.carRepository::save);


    }

    @Override
    public Car getRandomCar() {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1);

        return this.carRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CarsPrintDto> getCarByMake(String carBrand) {
        return this.carRepository.findAllByMakeOrderByModelAndOrderByTravelDistanceDesc(carBrand)
                .stream()
                .map(car -> modelMapper.map(car, CarsPrintDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarsWithPartsDto> getCarsWithParts() {
        return carRepository.findAll().stream().map(car -> {
            CarsWithPartsDto carsWithPartsDto = new CarsWithPartsDto();
            carsWithPartsDto.setCar(modelMapper.map(car, CarPartsDto.class));

            carsWithPartsDto.setParts(car.getPartList()
                    .stream()
                    .map(part -> modelMapper.map(part, PartsPrintDto.class))
                    .collect(Collectors.toList()));
            return carsWithPartsDto;
        })
                .collect(Collectors.toList());
    }


}
