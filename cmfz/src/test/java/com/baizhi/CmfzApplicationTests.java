package com.baizhi;

import com.baizhi.dao.CarouselDAO;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Carousel;
import com.baizhi.service.AdminService;
import com.baizhi.util.MyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {

    @Autowired
    AdminService adminService;

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

}
