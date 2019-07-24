package com.baizhi.service;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.dao.CarouselDAO;
import com.baizhi.entity.Carousel;
import com.baizhi.util.MyUtil;
import jdk.net.SocketFlow;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
@Slf4j
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselDAO carouselDAO;
    @Autowired
    private MyUtil myUtil;

    @Override
    public Carousel queryById(String id) {
        return carouselDAO.selectById(id);
    }
    @Override
    public Map<String,Object> queryAll(Integer pageNum,Integer pageSize) {
        Map<String, Object> map = myUtil.queryAllByPage(pageNum, pageSize, CarouselDAO.class,null);
        return map;
    }

    @Override
    @Transactional
    public void addCarousel(Carousel carousel) {
        try{
            String id = UUID.randomUUID().toString();
            carousel.setId(id);
            carousel.setStatus("正常显示");
            carousel.setCreateTime(new Date());
            carouselDAO.insertCarouse(carousel);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("addError");
        }
    }

    @Override
    @Transactional
    public void modifyCarousel(Carousel carousel) {
        Carousel selectCarousel = carouselDAO.selectById(carousel.getId());
        log.info("service selectById {}",selectCarousel);
        if(selectCarousel.getStatus().equals("正常显示")){
            selectCarousel.setStatus("不显示");
        }else{
            selectCarousel.setStatus("正常显示");
        }
        carouselDAO.updateCarouse(selectCarousel);
    }

    @Override
    @Transactional
    public void removeCarousel(String carouselId,HttpSession session) {
        myUtil.remove(CarouselDAO.class,carouselId,Carousel.class,"getImgPath","uploadImg",session);
    }
}
