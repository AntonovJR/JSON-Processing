package com.example.demo.service;

import com.example.demo.model.dto.ex1.SaleCustomerDto;
import com.example.demo.model.dto.ex6.SalesDiscountDto;

import java.util.List;

public interface SaleService {
    void seedSales();

    List<SaleCustomerDto> getSalesByUser(Long id);

    List<SalesDiscountDto> getAllSales();
}
