package com.example.demo.service;

import com.example.demo.model.dto.ex3.LocalSuppliersDto;
import com.example.demo.model.entity.PartSupplier;

import java.io.IOException;
import java.util.List;

public interface PartSupplierService {
    void seedSuppliers() throws IOException;

    PartSupplier findRandomSupplier();

    List<LocalSuppliersDto> getLocalSuppliers();
}
