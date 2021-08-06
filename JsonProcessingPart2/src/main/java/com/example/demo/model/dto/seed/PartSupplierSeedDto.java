package com.example.demo.model.dto.seed;


import com.google.gson.annotations.Expose;

public class PartSupplierSeedDto {
    @Expose
    private String name;
    @Expose
    private Boolean isImporter;

    public PartSupplierSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImporter() {
        return isImporter;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }
}
