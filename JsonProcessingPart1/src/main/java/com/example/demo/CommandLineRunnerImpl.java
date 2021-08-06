package com.example.demo;

import com.example.demo.model.dto.productsInRangeDto.ProductInRangeDto;
import com.example.demo.model.dto.productsByCategory.ProductsByCategoryDto;
import com.example.demo.model.dto.buyersDetailsDto.UserSoldDto;
import com.example.demo.model.dto.usersAndProductsDto.CountOfSellersDto;
import com.example.demo.model.dto.usersAndProductsDto.UsersDto;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String OUTPUT_FILES_PATH = "src/main/resources/files/out/";
    private static final String PRODUCT_IN_RANGE_FILE_NAME = "products-in-range.json";
    private static final String USER_SOLD_PRODUCTS_FILE_NAME = "users-sold-products.json";
    private static final String PRODUCTS_DETAILS_BY_CATEGORY_FILE_NAME = "categories-by-products.json";
    private static final String USER_SOLD_PRODUCTS_DETAILS_FILE_NAME = "users-and-products.json";
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService,
                                 ProductService productService, ModelMapper modelMapper, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        while(true) {
            System.out.println("number 99 will exit the program");
            System.out.println("Enter exercise num: ");
            int exNum = Integer.parseInt(bufferedReader.readLine());

            switch (exNum) {
                case 1 -> productsInRange();
                case 2 -> soldProducts();
                case 3 -> categoriesByProductsCount();
                case 4 -> usersAndProducts();
                case 99 -> System.exit(0);
                default -> System.out.println("Enter valid exercise number");
            }

        }
    }

    private void usersAndProducts() throws IOException {
        CountOfSellersDto countOfSellersDto = this.userService.findAllUsersWithSales();

        String content = gson.toJson(countOfSellersDto);

        writeToFile(OUTPUT_FILES_PATH+USER_SOLD_PRODUCTS_DETAILS_FILE_NAME,content);
    }

    private void categoriesByProductsCount() throws IOException {
        List<ProductsByCategoryDto> productsByCategoryDtoList = categoryService.productsDetailsByCategory();

        String content = gson.toJson(productsByCategoryDtoList);

        writeToFile(OUTPUT_FILES_PATH+PRODUCTS_DETAILS_BY_CATEGORY_FILE_NAME,content);
    }

    private void soldProducts() throws IOException {
        List<UserSoldDto> userSoldDtos =
                userService.findAllUsersWithMoreThanOneSoldProducts();

        String content = gson.toJson(userSoldDtos);

        writeToFile(OUTPUT_FILES_PATH+USER_SOLD_PRODUCTS_FILE_NAME,content);
    }

    private void productsInRange() throws IOException {
        List<ProductInRangeDto> productInRangeDtoList = productService
                .findAllProductsInRangeOrderByPrice(new BigDecimal(500L), new BigDecimal(1000L));
        String content = gson.toJson(productInRangeDtoList);

        writeToFile(OUTPUT_FILES_PATH+PRODUCT_IN_RANGE_FILE_NAME,content);
    }

    private void writeToFile(String path, String content) throws IOException {
        Files.write(Path.of(path), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();

    }
}
