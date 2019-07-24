package com.baizhi.dao;

import com.baizhi.entity.Carousel;

import java.util.List;
import java.util.Map;

public interface CarouselDAO {
    List<Carousel> selectAll(Map map);
    Integer selectCount();
    Carousel selectById(String id);
    void insertCarouse(Carousel carousel);
    void updateCarouse(Carousel carousel);
    void deleteById(String carouselId);
}
