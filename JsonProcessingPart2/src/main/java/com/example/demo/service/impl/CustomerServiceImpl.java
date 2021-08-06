package com.example.demo.service.impl;

import com.example.demo.model.dto.ex5.CustomerCarsDto;
import com.example.demo.model.dto.seed.CustomerSeedDto;
import com.example.demo.model.entity.Car;
import com.example.demo.model.entity.Customer;
import com.example.demo.model.entity.Part;
import com.example.demo.model.entity.Sale;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "customers.json";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;

    }

    @Override
    public void seedCustomers() throws IOException {
        if (this.customerRepository.count() > 0) {
            return;
        }

        String data = Files.readString(Path.of(RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME));

        CustomerSeedDto[] customerSeedDtos = gson.fromJson(data, CustomerSeedDto[].class);

        Arrays.stream(customerSeedDtos)
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                    customer.setBirthDate(LocalDateTime.parse(customerSeedDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    return customer;
                })
                .forEach(this.customerRepository::save);
    }

    @Override
    public long getCarsCount() {
        return this.customerRepository.count();
    }

    @Override
    public Customer getCustomerById(long i) {
        return this.customerRepository.findById(i).orElse(null);
    }

    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll().stream()
                .sorted(Comparator.comparing(Customer::getBirthDate).thenComparing(Customer::getYoungDriver))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerCarsDto> getCustomerCars() {
        return customerRepository.getAllBySaleListBiggerThanOne()
                .stream()
                .map(customer -> {
                    CustomerCarsDto customerCarsDto = new CustomerCarsDto();
                    customerCarsDto.setFullName(customer.getName());
                    customerCarsDto.setBoughtCars(customer.getSaleList().size());
                    List<Car> cars = customer.getSaleList().stream().map(Sale::getCar).collect(Collectors.toList());
                    List<BigDecimal> discount = customer.getSaleList().stream().map(Sale::getDiscountPercentage)
                            .collect(Collectors.toList());

                    BigDecimal spendMoney = BigDecimal.valueOf(0);
                    for (Car car : cars) {
                        List<Part> parts = car.getPartList();
                        for (Part part : parts) {
                            spendMoney = spendMoney.add(part.getPrice());
                        }
                    }
                    spendMoney = spendMoney.multiply(BigDecimal.valueOf(1L).subtract(discount.get(0).divide(BigDecimal.valueOf(100L))));
                    customerCarsDto.setSpentMoney(spendMoney);
                    return customerCarsDto;
                })
                .collect(Collectors.toList());
    }
}
