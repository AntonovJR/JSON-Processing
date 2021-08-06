package com.example.demo.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "customers")
public class Customer extends BaseEntity{
    //Customer has name, date of birth and info whether he/she is a young driver (Young driver is a driver that
    //has less than 2 years of experience. Those customers get additional 5% off for the sale.).
    private String name;
    @Column(name = "birth_date")
    private LocalDateTime birthDate;
    @Column(name = "is_young_driver")
    private Boolean isYoungDriver;
    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private List<Sale> saleList;


    public Customer() {
    }

    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime dateOfBirth) {
        this.birthDate = dateOfBirth;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
