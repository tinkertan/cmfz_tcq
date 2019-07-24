package com.baizhi;

import com.baizhi.dao.CarouselDAO;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Carousel;
import com.baizhi.service.AdminService;
import com.baizhi.service.CarouselService;
import com.baizhi.util.MyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {

    @Autowired
    AdminService adminService;
    @Autowired
    CarouselDAO carouselDAO;
    @Autowired
    CarouselService carouselService;

    @Autowired
    MyUtil myUtil;
    @Test
    public void testMyUtilRemove() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object remove = myUtil.remove(CarouselDAO.class, "111",Carousel.class
                , "getImgPath", "uploadImg", null);
    }

    @Test
    public void testGetBean(){
        CarouselDAO carouselDAO1 = myUtil.getBean(CarouselDAO.class);
        System.out.println(carouselDAO1);
    }

    @Test
    public void testDeleteImg(){
        new File("C:\\Users\\HP\\IdeaProjects\\cmfz_tcq\\cmfz\\src\\main\\webapp\\uploadImg\\1563024276314").delete();
    }

    @Test
    public void testQueryRedis(){
        Map<String, Object> map = carouselService.queryAll(1, 3);
        System.out.println(map);
    }
    @Test
    public void testAddRedis(){
        carouselService.addCarousel(new Carousel(UUID.randomUUID().toString(),"123","123","zhengchang",new Date()));
    }

}
