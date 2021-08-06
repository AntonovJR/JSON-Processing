package com.example.demo.service;

import com.example.demo.model.entity.Part;

import java.io.IOException;
import java.util.List;

public interface PartService {
    void seedParts() throws IOException;

    List<Part> getRandomPartsList();
}
