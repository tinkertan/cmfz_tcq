package com.baizhi.dao;

import com.baizhi.entity.Guru;

import javax.management.Query;
import java.util.List;
import java.util.Map;

public interface GuruDAO {
    List<Guru> selectAll(Map map);
    Integer selectCount();
    List<Guru> selectAllNoPage();
    Guru selectById(String id);
    void insert(Guru guru);
    void update(Guru guru);
}
