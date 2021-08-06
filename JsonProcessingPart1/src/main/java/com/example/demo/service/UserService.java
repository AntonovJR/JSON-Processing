package com.example.demo.service;

import com.example.demo.model.dto.buyersDetailsDto.UserSoldDto;
import com.example.demo.model.dto.usersAndProductsDto.CountOfSellersDto;
import com.example.demo.model.dto.usersAndProductsDto.UsersDto;
import com.example.demo.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;
    User findRandomUser();

    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts();

    CountOfSellersDto findAllUsersWithSales();
}

