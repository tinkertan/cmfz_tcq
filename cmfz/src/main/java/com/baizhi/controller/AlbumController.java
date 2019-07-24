package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import com.baizhi.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private MyUtil myUtil;

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        return albumService.queryAll(page,rows);
    }

    @RequestMapping("edit")
    public String  edit(Album album,String oper, HttpSession session,String[] id){
        if("add".equals(oper)){
            String s = albumService.addAlbum(album);
            return s;
        }else if("edit".equals(oper)){
            String s = albumService.modifyAlbum(album, session);
            return s;
        }else {
            for(String i:id){
                albumService.removeAlbum(i,session);
            }
        }
        return null;
    }
    @RequestMapping("upload")
    public void upload(String id,MultipartFile cover,HttpSession session) {
        String originalFilename = cover.getOriginalFilename();
        if(!(originalFilename==null||"".equals(originalFilename))){
            long time = new Date().getTime();
            String fileName = time+originalFilename;
            String realPath = session.getServletContext().getRealPath("uploadCover");
            try {
                cover.transferTo(new File(realPath,fileName));
                Album album = new Album();
                album.setCover(fileName);
                album.setId(id);
                albumService.modifyAlbum(album,session);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping("opt")
    public Map<String,Object> optAlbum(Integer pageSize,Album album, MultipartFile uploadFile, HttpSession session){
        try{
            if(uploadFile!=null){
                String filename = uploadFile.getOriginalFilename();
                long time = new Date().getTime();
                String realPath = session.getServletContext().getRealPath("/uploadImg");
                uploadFile.transferTo(new File(realPath+"\\"+time+filename));
                album.setCover(time+filename);
                if(album.getId()==null){
                    albumService.addAlbum(album);
                }else {
                    albumService.modifyAlbum(album,session);
                }
            }else {
                albumService.modifyAlbum(album,null);
            }
        }catch (Exception e){
            e.printStackTrace();
            myUtil.deleteFile("uploadImg",album.getCover(),session);
        }
        return queryAll(1,pageSize);
    }

    @RequestMapping("remove")
    public Map<String,Object> remove(Integer pageSize,String[] ids,HttpSession session){
        for(String id:ids){
            albumService.removeAlbum(id,session);
        }
        return queryAll(1,pageSize);
    }
    @RequestMapping("queryById")
    public Album queryById(String ids){
        return albumService.queryByAlbumId(ids);
    }

}
