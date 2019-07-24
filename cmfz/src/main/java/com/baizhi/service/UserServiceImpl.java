package com.baizhi.service;

import com.baizhi.dao.UserDAO;
import com.baizhi.entity.Modeldto;
import com.baizhi.entity.User;
import com.baizhi.util.MD5Utils;
import com.baizhi.util.MyUtil;
import com.google.gson.Gson;
import io.goeasy.GoEasy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements Userservice{
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MyUtil myUtil;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<User> queryAllNoPage() {
        return userDAO.selectAllNoPage();
    }

    @Override
    public Map<String,Object>  addUser(User user) {
        User user1 = userDAO.selectByPhone(user.getPhone());
        if(user1!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("error","-400");
            map.put("message","手机号已存在");
            return map;
        }
        String s = UUID.randomUUID().toString();
        user.setId(s);
        user.setRegistTime(new Date());
        String salt = MD5Utils.getSalt();
        user.setSalt(salt);
        String password = MD5Utils.getPassword(salt + user.getPassword());
        user.setPassword(password);
        user.setStatus("正常");
        userDAO.insertUser(user);
        Map<String, Object> map = stats();
        Gson gson = new Gson();
        String json = gson.toJson(map);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-8ea535922c6245859cd20df68361f338");
        goEasy.publish("demo_channel", json);
        return artMap(user);
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> queryByPhone(String phone,String password,String code,String sendCode) {
        User user = userDAO.selectByPhone(phone);
        if(user==null){
            Map<String,Object> map = new HashMap<>();
            map.put("error",-200);
            map.put("message","用户名不存在");
            return map;
        }else {
            String password1 = "";
            if(password!=null){
                password1 = MD5Utils.getPassword(user.getSalt() + password);
            }
            if((user.getPassword().equals(password1))||(sendCode!=null&&sendCode.equals(code))){
                return artMap(user);
            }else {
                Map<String,Object> map = new HashMap<>();
                map.put("error",-200);
                map.put("message","密码或验证码不正确");
                return map;
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        return myUtil.queryAllForJqgrid(page,rows,UserDAO.class,null);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public User queryById(String id) {
        return userDAO.selectById(id);
    }

    @Override
    public void update(User user) {
        userDAO.updateUser(user);
    }

    private Map<String,Object> artMap(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",user.getId());
        map.put("phone",user.getPhone());
        map.put("password",user.getPassword());
        map.put("salt",user.getSalt());
        map.put("dharmaName",user.getDharmaname());
        map.put("province",user.getProvince());
        map.put("city",user.getCity());
        map.put("gender",user.getGender());
        map.put("personalSign",user.getPersonalSign());
        map.put("profile",user.getProfile());
        map.put("status",user.getStatus());
        map.put("registTime",user.getRegistTime());
        return map;
    }

    @Override
    public Map<String,Object> stats(){
        List<Modeldto> list = userDAO.selectByMonth();
        List<String> months = new ArrayList<>();
        List<Integer> personNums = new ArrayList<>();
        for (Modeldto month : list) {
            months.add(month.getName());
            personNums.add(month.getValue());
        }
        List<Modeldto> areaMan = userDAO.selectByGender("男");
        List<Modeldto> areaWoman = userDAO.selectByGender("女");
        Map<String,Object> map = new HashMap<>();
        map.put("month",months);
        map.put("personNum",personNums);
        map.put("areaMan",areaMan);
        map.put("areaWoman",areaWoman);
        log.info("service  stats 返回的{}",map);
        return map;
    }
}
