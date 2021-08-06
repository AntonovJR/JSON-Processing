package com.example.demo.repository;

import com.example.demo.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    @Query("select c from cars c where c.make = ?1 order by c.model, c.travelledDistance desc")
    List<Car> findAllByMakeOrderByModelAndOrderByTravelDistanceDesc(String carBrand);


    @Override
    List<Car> findAll();
}
