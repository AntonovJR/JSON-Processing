package com.example.demo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity(name = "cars")
public class Car extends  BaseEntity {
//make, model, and travelled distance in kilometers.
    private String make;
    private String model;
    @Column(name = "travelled_distance")
    private Long travelledDistance;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Part> partList;

    public Car() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<Part> getPartList() {
        return partList;
    }

    public void setPartList(List<Part> partList) {
        this.partList = partList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return make.equals(car.make)
                && model.equals(car.model)
                && travelledDistance.equals(car.travelledDistance)
                && partList.equals(car.partList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, travelledDistance, partList);
    }
}
