package com.baizhi.service;

import com.baizhi.entity.Carousel;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface CarouselService {
    Map<String,Object> queryAll(Integer pageNum,Integer pageSize );
    Carousel queryById(String id);
    void addCarousel(Carousel carousel);
    void modifyCarousel(Carousel carousel);
    void removeCarousel(String carouselId, HttpSession session);
}
