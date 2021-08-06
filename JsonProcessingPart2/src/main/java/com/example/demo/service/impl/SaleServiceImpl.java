package com.example.demo.service.impl;

import com.example.demo.model.dto.ex1.CarSaleDto;
import com.example.demo.model.dto.ex1.SaleCustomerDto;
import com.example.demo.model.dto.ex6.SalesDiscountDto;
import com.example.demo.model.entity.Car;
import com.example.demo.model.entity.Customer;
import com.example.demo.model.entity.Part;
import com.example.demo.model.entity.Sale;
import com.example.demo.repository.SaleRepository;
import com.example.demo.service.CarService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CustomerService customerService;
    private final CarService carService;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CustomerService customerService, CarService carService, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.customerService = customerService;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedSales() {
        if (saleRepository.count() > 0) {
            return;
        }
        long count = customerService.getCarsCount();
        for (long i = 1; i <= count; i++) {
            Sale sale = new Sale();
            Customer customer = customerService.getCustomerById(i);
            Car car = carService.getRandomCar();
            sale.setCustomer(customer);
            try {
                sale.setCar(car);
            } catch (Throwable e) {
                i--;
            }
            BigDecimal[] discounts = new BigDecimal[]{
                    BigDecimal.valueOf(0),
                    BigDecimal.valueOf(5),
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(15),
                    BigDecimal.valueOf(20),
                    BigDecimal.valueOf(30),
                    BigDecimal.valueOf(40),
                    BigDecimal.valueOf(50)
            };
            int random = ThreadLocalRandom.current().nextInt(0, discounts.length);
            BigDecimal discount = discounts[random];
            if (customer.getYoungDriver()) {

                discount = discount.add(BigDecimal.valueOf(5L));

            }
            sale.setDiscountPercentage(discount);

            saleRepository.save(sale);
        }


    }

    @Override
    public List<SaleCustomerDto> getSalesByUser(Long id) {
        List<Sale> sales = saleRepository.findAllByCustomer_Id(id);
        return sales
                .stream()
                .map(sale -> {
                    Car car = sale.getCar();
                    SaleCustomerDto saleCustomerDto = modelMapper.map(sale, SaleCustomerDto.class);
                    saleCustomerDto.setCarSaleDto(modelMapper.map(car, CarSaleDto.class));
                    return saleCustomerDto;
                })

                .collect(Collectors.toList());
    }

    @Override
    public List<SalesDiscountDto> getAllSales() {
        return saleRepository.findAll() .stream()
                .map(sale -> {
                    Car car = sale.getCar();
                    SalesDiscountDto salesDiscountDto = modelMapper.map(sale, SalesDiscountDto.class);
                    salesDiscountDto.setCar(modelMapper.map(car, CarSaleDto.class));
                    List<Part> parts = car.getPartList();
                    BigDecimal price = BigDecimal.valueOf(0);
                    for (Part part : parts) {
                        price = price.add(part.getPrice());
                                            }
                    salesDiscountDto.setPrice(price);
                    BigDecimal discountedPrice = price.multiply(BigDecimal.valueOf(1L).subtract(sale.getDiscountPercentage().divide(BigDecimal.valueOf(100L))));
                    salesDiscountDto.setPriceWithDiscount(discountedPrice);
                    return salesDiscountDto;
                })

                .collect(Collectors.toList());
    }


}