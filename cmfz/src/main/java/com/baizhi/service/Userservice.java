package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface Userservice {
    Map<String,Object> queryAll(Integer page,Integer rows);
    User queryById(String id);
    void update(User user);

    List<User> queryAllNoPage();

    Map<String,Object>  addUser(User user);
    Map<String,Object>  queryByPhone(String phone,String password,String code,String sendcode);
    public Map<String,Object> stats();
}
