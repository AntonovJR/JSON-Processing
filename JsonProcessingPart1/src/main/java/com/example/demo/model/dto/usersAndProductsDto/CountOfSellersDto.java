package com.example.demo.model.dto.usersAndProductsDto;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class CountOfSellersDto {
    @Expose
    private Integer usersCount;
    @Expose
    private List<UsersDto> users;

    public CountOfSellersDto() {
        users = new ArrayList<>();
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UsersDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersDto> users) {
        this.users = users;
    }
}
