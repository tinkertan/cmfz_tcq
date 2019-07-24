package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;

import org.apache.catalina.util.URLEncoder;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows,String albumId){
        return chapterService.queryAll(page,rows,albumId);
    }

    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper,String[] id, HttpSession session){
        if("add".equals(oper)){
            String s = chapterService.addChapter(chapter);
            return s;
        }else{
            for(String ii:id){
                chapterService.removeChapter(ii,session);
            }
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(String id,MultipartFile downPath,HttpSession session){
        String originalFilename = downPath.getOriginalFilename();
        String realPath = session.getServletContext().getRealPath("/uploadChapter");
        long time = new Date().getTime();
        String fileName = time+originalFilename;
        try {
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setDownPath(fileName);
            downPath.transferTo(new File(realPath,fileName));
            chapterService.updateDownPath(chapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("download")
    public void download(String fileName, HttpSession session , HttpServletResponse response){
        try {
            String realPath = session.getServletContext().getRealPath("/uploadChapter");
            byte[] bytes = FileUtils.readFileToByteArray(new File(realPath + "/" + fileName));
            response.setHeader("content-disposition","attachment");
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
