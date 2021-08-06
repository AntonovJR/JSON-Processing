package com.example.demo;


import com.example.demo.model.dto.ex5.CustomerCarsDto;
import com.example.demo.model.dto.ex4.CarsWithPartsDto;
import com.example.demo.model.dto.ex2.CarsPrintDto;
import com.example.demo.model.dto.ex3.LocalSuppliersDto;
import com.example.demo.model.dto.ex1.OrderedCustomerDto;
import com.example.demo.model.dto.ex6.SalesDiscountDto;
import com.example.demo.service.*;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String OUTPUT_FILES_PATH = "src/main/resources/files/out/";
    private static final String CUSTOMER_FILE_NAME = "ordered-customers.json";
    private static final String CARS_FROM_MAKE_FILE_NAME = "toyota-cars.json";
    private static final String LOCAL_SUPPLIERS_FILE_NAME = "local-suppliers.json";
    private static final String CARS_WITH_PARTS_FILE_NAME = "cars-and-parts.json";
    private static final String CUSTOMER_CARS_COSTS_FILE_NAME = "customers-total-sales.json";
    private static final String CAR_SALE_FILE_NAME = "sales-discounts.json";

    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final PartSupplierService partSupplierService;
    private final SaleService saleService;
    private final BufferedReader bufferedReader;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public CommandLineRunnerImpl(CarService carService, CustomerService customerService, PartService partService,
                                 PartSupplierService partSupplierService, SaleService saleService, Gson gson, ModelMapper modelMapper) {
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.partSupplierService = partSupplierService;
        this.saleService = saleService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        while (true) {
            System.out.println("Please enter exercise number: ");
            int exNum = Integer.parseInt(bufferedReader.readLine());
            switch (exNum) {
                case 1 -> getAllCustomers();
                case 2 -> carsFromMakeToyota();
                case 3 -> getLocalSuppliers();
                case 4 -> getCarsAndParts();
                case 5 -> totalSalesByCustomer();
                case 6 -> salesWithAppliedDiscount();
                case 99 -> System.exit(0);
                default -> System.out.println("Please enter valid exercise number");
            }


        }
    }

    private void salesWithAppliedDiscount() throws IOException {
        List<SalesDiscountDto> salesDiscountDtos = saleService.getAllSales();
        String content = gson.toJson(salesDiscountDtos);

        writeToFile(OUTPUT_FILES_PATH + CAR_SALE_FILE_NAME, content);
    }

    private void totalSalesByCustomer() throws IOException {
        List<CustomerCarsDto> customerCarsDtos = customerService.getCustomerCars();
        String content = gson.toJson(customerCarsDtos);

        writeToFile(OUTPUT_FILES_PATH + CUSTOMER_CARS_COSTS_FILE_NAME, content);
    }

    private void getCarsAndParts() throws IOException {
        List<CarsWithPartsDto> carsWithPartsDtos = carService.getCarsWithParts();
        String content = gson.toJson(carsWithPartsDtos);

        writeToFile(OUTPUT_FILES_PATH + CARS_WITH_PARTS_FILE_NAME, content);
    }

    private void getLocalSuppliers() throws IOException {
        List<LocalSuppliersDto> localSuppliersDtos = partSupplierService.getLocalSuppliers();
        String content = gson.toJson(localSuppliersDtos);

        writeToFile(OUTPUT_FILES_PATH + LOCAL_SUPPLIERS_FILE_NAME, content);
    }

    private void carsFromMakeToyota() throws IOException {
        List<CarsPrintDto> carsPrintDtos = carService.getCarByMake("Toyota");

        String content = gson.toJson(carsPrintDtos);

        writeToFile(OUTPUT_FILES_PATH + CARS_FROM_MAKE_FILE_NAME, content);

    }

    private void getAllCustomers() throws IOException {
        List<OrderedCustomerDto> orderedCustomerDtos = customerService
                .findAll()
                .stream()
                .map(customer -> {
                    OrderedCustomerDto orderedCustomerDto = modelMapper.map(customer, OrderedCustomerDto.class);
                    orderedCustomerDto.setSales(saleService.getSalesByUser(customer.getId()));
                    return orderedCustomerDto;
                })
                .collect(Collectors.toList());

        String content = gson.toJson(orderedCustomerDtos);

        writeToFile(OUTPUT_FILES_PATH + CUSTOMER_FILE_NAME, content);
    }

    private void writeToFile(String path, String content) throws IOException {
        Files.write(Path.of(path), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        partSupplierService.seedSuppliers();
        partService.seedParts();
        carService.seedCars();
        customerService.seedCustomers();
        saleService.seedSales();


    }
}
