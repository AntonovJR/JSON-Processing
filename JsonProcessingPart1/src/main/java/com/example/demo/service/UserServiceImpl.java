package com.example.demo.service;

import com.example.demo.model.dto.buyersDetailsDto.UserSoldDto;
import com.example.demo.model.dto.seed.UserSeedDto;
import com.example.demo.model.dto.usersAndProductsDto.CountOfSellersDto;
import com.example.demo.model.dto.usersAndProductsDto.ProductsDetailsDto;
import com.example.demo.model.dto.usersAndProductsDto.SoldProductsDto;
import com.example.demo.model.dto.usersAndProductsDto.UsersDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.ValidationUtil;
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

import static com.example.demo.constants.GlobalConstants.RESOURCE_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {
    private static final String USERS_FILE_NAME = "users.json";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() == 0) {
            Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCE_FILE_PATH + USERS_FILE_NAME)),
                    UserSeedDto[].class))
                    .filter(validationUtil::isValid)
                    .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                    .forEach(userRepository::save);
        }
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);
        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts() {

        return userRepository.findAllUsersWithMoreThanOneSoldProductsOrderByLastNameThenFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CountOfSellersDto findAllUsersWithSales() {
        List<User> users = this.userRepository.findAllUsersWithMoreThanOneSoldProductsOrderBySoldProducts();
        CountOfSellersDto usersCountDto = new CountOfSellersDto();
        List<UsersDto> usersAndProductsDtos = users.stream().map(user -> {
                    UsersDto userDto = modelMapper.map(user, UsersDto.class);

                    List<ProductsDetailsDto> productDtos = user.getSoldProducts().stream()
                            .map(product -> modelMapper.map(product, ProductsDetailsDto.class))
                            .collect(Collectors.toList());

                    SoldProductsDto products = new SoldProductsDto();
                    products.setSoldProducts(productDtos);
                    products.setCount(productDtos.size());
                    userDto.setSoldProducts(products);

                    return userDto;
                }
        ).collect(Collectors.toList());
        usersCountDto.setUsers(usersAndProductsDtos);
        usersCountDto.setUsersCount(users.size());
        return usersCountDto;
    }
}
