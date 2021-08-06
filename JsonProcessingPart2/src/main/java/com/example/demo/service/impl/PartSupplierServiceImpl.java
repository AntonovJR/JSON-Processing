package com.example.demo.service.impl;

import com.example.demo.model.dto.ex3.LocalSuppliersDto;
import com.example.demo.model.dto.seed.PartSupplierSeedDto;
import com.example.demo.model.entity.PartSupplier;
import com.example.demo.repository.PartSupplierRepository;
import com.example.demo.service.PartSupplierService;
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
public class PartSupplierServiceImpl implements PartSupplierService {
    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "suppliers.json";
    private final PartSupplierRepository partSupplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public PartSupplierServiceImpl(PartSupplierRepository partSupplierRepository, ModelMapper modelMapper, Gson gson) {
        this.partSupplierRepository = partSupplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSuppliers() throws IOException {
        if (partSupplierRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME));
        PartSupplierSeedDto[] partSupplierSeedDtos = gson.fromJson(fileContent, PartSupplierSeedDto[].class);

        Arrays.stream(partSupplierSeedDtos)
                .map(partSupplierSeedDto -> modelMapper.map(partSupplierSeedDto, PartSupplier.class))
                .forEach(partSupplierRepository::save);


    }

    @Override
    public PartSupplier findRandomSupplier() {
        long randomId = ThreadLocalRandom.current().nextLong(1, partSupplierRepository.count() + 1);

        return partSupplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<LocalSuppliersDto> getLocalSuppliers() {
        return partSupplierRepository.findAllByIsImporter(false)
                .stream()
                .map(partSupplier -> {
                    LocalSuppliersDto localSuppliersDto =modelMapper.map(partSupplier,LocalSuppliersDto.class);
                    localSuppliersDto.setPartsCount(partSupplier.getPartList().size());
                    return localSuppliersDto;
                })
                .collect(Collectors.toList());

    }
}
