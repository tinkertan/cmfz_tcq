package com.baizhi.controller;

import com.baizhi.entity.Carousel;
import com.baizhi.service.CarouselService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer pageNum,Integer pageSize){
        Map<String, Object> map = carouselService.queryAll(pageNum,pageSize);
        return map;
    }
    @RequestMapping("optCarousel")
    public Map<String,Object> addCarousel(Carousel carousel, MultipartFile imgFile, HttpSession session,Integer pageSize){
        String imgPath = null;
        try {
            String imgName = imgFile.getOriginalFilename();
            String realPath = session.getServletContext().getRealPath("/uploadImg");
            long time = new Date().getTime();
            imgPath = realPath+"\\"+time+imgName;
            imgFile.transferTo(new File(imgPath));
            carousel.setImgPath(time+imgName);

        } catch (IOException e) {
            e.printStackTrace();
            if("addError".equals(e.getMessage())){
                try {
                    FileUtils.forceDelete(new File(imgPath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        carouselService.addCarousel(carousel);
        return queryAll(1,pageSize);
    }
    @RequestMapping("updateCarousel")
    public Map<String,Object> updateCarousel(String[] ids,Integer pageSize){
        for (String id :ids){
            Carousel carousel = new Carousel();
            carousel.setId(id);
            carouselService.modifyCarousel(carousel);
        }
        return queryAll(1,pageSize);
    }
    @RequestMapping("deleteCarousel")
    public Map<String,Object> removeCarousel(String[] ids,Integer pageSize,HttpSession session){
        for (String id :ids){
            carouselService.removeCarousel(id,session);
        }
        return queryAll(1,pageSize);
    }
    @RequestMapping("queryById")
    public Carousel queryById(String id){
        return carouselService.queryById(id);
    }
}
