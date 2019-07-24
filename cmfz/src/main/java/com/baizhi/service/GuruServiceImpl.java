package com.baizhi.service;

import com.baizhi.dao.GuruDAO;
import com.baizhi.entity.Guru;
import com.baizhi.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class GuruServiceImpl implements GuruService{
    @Autowired
    protected GuruDAO guruDAO;
    @Autowired
    private MyUtil myUtil;

    @Override
    public List<Guru> queryAllNoPage() {
        return guruDAO.selectAllNoPage();
    }

    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        return myUtil.queryAllForJqgrid(page,rows,GuruDAO.class,null);
    }

    @Override
    @Transactional
    public String addGuru(Guru guru) {
        String s = UUID.randomUUID().toString();
        guru.setId(s);
        guruDAO.insert(guru);
        return s;
    }

    @Override
    public Guru queryById(String id) {
        return guruDAO.selectById(id);
    }

    @Override
    @Transactional
    public String modifyGuru(Guru guru) {
        guruDAO.update(guru);
        return guru.getId();
    }
}
