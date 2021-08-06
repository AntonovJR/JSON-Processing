package com.example.demo.repository;

import com.example.demo.model.entity.PartSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartSupplierRepository extends JpaRepository<PartSupplier,Long> {

    List<PartSupplier> findAllByIsImporter(Boolean isNotImporter);
}
