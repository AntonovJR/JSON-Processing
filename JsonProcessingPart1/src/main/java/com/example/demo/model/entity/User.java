package com.example.demo.model.entity;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    private Integer age;
    @ManyToMany
    private List<User> friends;
    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER)
    private Set<Product> soldProducts;


    public User() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }


    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friendId) {
        this.friends = friendId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
