package com.baizhi.service;

import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    Map<String,Object> queryAll(Integer page,Integer rows);
    List<Guru> queryAllNoPage();
    Guru queryById(String id);
    String addGuru(Guru guru);
    String modifyGuru(Guru guru);
}
