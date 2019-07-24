package com.baizhi.service;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDAO adminDAO;

    @Override
    public List<Admin> queryAll() {
        List<Admin> admins = adminDAO.selectAll();
        return admins;
    }


    @Override
    public Admin login(Admin admin) {
        Admin admin1 = adminDAO.selectByName(admin.getUsername());
        log.info("server login {}",admin);
        if (admin1==null) throw new RuntimeException("账号不存在");
        if(!admin1.getPassword().equals(admin.getPassword())) throw new RuntimeException("账号或密码不正确");
        log.info("server login return {}",admin1);
        return admin1;
    }
}

