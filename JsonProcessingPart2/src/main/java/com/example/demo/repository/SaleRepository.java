package com.example.demo.repository;

import com.example.demo.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {


    List<Sale> findAllByCustomer_Id(long id);
    List<Sale> findAll();
}
