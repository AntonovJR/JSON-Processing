package com.example.demo.repository;

import com.example.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
            "where(select count(p) from Product p where p.seller.id=u.id)>0" +
            " order by  u.lastName, u.firstName")
    List<User> findAllUsersWithMoreThanOneSoldProductsOrderByLastNameThenFirstName();

    @Query("SELECT u from User u " +
            "where u.soldProducts.size>0 " +
            "order by u.soldProducts.size desc, u.lastName")
    List<User> findAllUsersWithMoreThanOneSoldProductsOrderBySoldProducts();


}
