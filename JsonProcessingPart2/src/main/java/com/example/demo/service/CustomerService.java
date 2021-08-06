package com.example.demo.service;

import com.example.demo.model.dto.ex5.CustomerCarsDto;
import com.example.demo.model.entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    void seedCustomers() throws IOException;

    long getCarsCount();

    Customer getCustomerById(long i);

    List<Customer> findAll();

    List<CustomerCarsDto> getCustomerCars();
}
