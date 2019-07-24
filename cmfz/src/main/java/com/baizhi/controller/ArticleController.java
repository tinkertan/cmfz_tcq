package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        return articleService.queryAll(page,rows);
    }

    @RequestMapping("edit")
    public String edit(Article article, String[] id, String oper, HttpSession session){
        if ("".equals(oper)||oper==null) {
            return articleService.addArticle(article);
        }else if("del".equals(oper)){
            for(String ii:id){
                articleService.removeArticle(ii,session);
            }
        }
        return null;
    }

    @RequestMapping("upload")
    public Map<String, Object> upload(String id, MultipartFile content, HttpSession session){
        String originalFilename = content.getOriginalFilename();
        String realPath = session.getServletContext().getRealPath("/uploadContent");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }
        Map<String,Object> map = new HashMap<>();
        try{
            content.transferTo(new File(realPath,originalFilename));
            map.put("error",0);
            map.put("url","http://localhost:8081/cmfz/uploadContent/"+originalFilename);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("error",1);
            map.put("url","http://localhost:8081/cmfz/uploadContent/"+originalFilename);
            return map;
        }
    }
    @RequestMapping("showPic")
    public Map<String,Object> showPic(HttpSession session){
        String realPath = session.getServletContext().getRealPath("/uploadContent");
        File file = new File(realPath);
        String[] list = file.list();
        Map<String ,Object> map = new HashMap<>();
        map.put("current_url","http://localhost:8081/cmfz/uploadContent/");
        map.put("total_count",list.length);
        List<Object> list1 = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            String s = list[i];
            Map<String,Object> map1 = new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            long length = file.length();
            map1.put("filesize",length);
            map1.put("is_phono",true);
            String substring = s.substring(s.lastIndexOf(".") + 1);
            map1.put("filetype",substring);
            map1.put("filename",s);
            map1.put("datetime",new Date());
            list1.add(map1);
        }
        map.put("file_list",list1);
        return map;
    }
    @RequestMapping("queryById")
    public Article queryById(String id){
       return articleService.queryById(id);
    }
}
