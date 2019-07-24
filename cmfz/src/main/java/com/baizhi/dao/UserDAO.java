package com.baizhi.dao;

import com.baizhi.entity.Modeldto;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    List<User> selectAll(Map map);
    User selectById(String id);
    Integer selectCount();
    void updateUser(User user);
    List<User> selectAllNoPage();

    void insertUser(User user);
    User selectByPhone(String phone);
    List<Modeldto> selectByMonth();

    List<Modeldto> selectByGender(String gender);
}
