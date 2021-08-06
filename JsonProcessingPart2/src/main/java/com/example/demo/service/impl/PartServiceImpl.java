package com.example.demo.service.impl;

import com.example.demo.model.dto.seed.PartsSeedDto;
import com.example.demo.model.entity.Part;
import com.example.demo.repository.PartRepository;
import com.example.demo.service.PartService;
import com.example.demo.service.PartSupplierService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {
    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "parts.json";
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final PartSupplierService partSupplierService;

    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson,
                           PartSupplierService partSupplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.partSupplierService = partSupplierService;
    }

    @Override
    public void seedParts() throws IOException {
        if ( this.partRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME));
        PartsSeedDto[] partsSeedDtos =  this.gson.fromJson(fileContent, PartsSeedDto[].class);

        Arrays.stream(partsSeedDtos)
                .map(productSeedDto -> {
                    Part part =  this.modelMapper.map(productSeedDto, Part.class);
                    part.setPartSupplier( this.partSupplierService.findRandomSupplier());
                    return part;
                })
                .forEach( this.partRepository::save);
    }

    @Override
    public List<Part> getRandomPartsList() {
        List<Part> partList = new ArrayList<>();
        int randomSize = ThreadLocalRandom.current().nextInt(3, 6);
        long repoSize = this.partRepository.count();

        for (int i = 0; i < randomSize ; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, repoSize+1);
            partList.add(this.partRepository.findById(randomId).orElse(null));
        }
        return partList;
    }
}
