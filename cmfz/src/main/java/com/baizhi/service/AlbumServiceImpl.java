package com.baizhi.service;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.entity.Album;
import com.baizhi.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)

public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private MyUtil myUtil;
    @Override
    public Map<String,Object>  queryAll(Integer pageNum,Integer pageSize) {
        Map<String, Object> map = myUtil.queryAllForJqgrid(pageNum, pageSize, AlbumDAO.class,null);
        return map;
    }

    @Override
    public Album queryByAlbumId(String albumId) {
        return albumDAO.selectById(albumId);
    }

    @Override
    @Transactional
    public String addAlbum(Album album) {
        String s = UUID.randomUUID().toString();
        album.setId(s);
        if(album.getCover()==null){
            album.setCover("empty");
        }
        if(album.getCount()==null){
            album.setCount(0);
        }
        if(album.getAuthor()==null){
            album.setAuthor("未知");
        }
        if(album.getBroadcast()==null){
            album.setBroadcast("未知");
        }
        if(album.getBrief()==null){
            album.setBrief("无");
        }
        if(album.getPublishTime()==null){
            album.setPublishTime(new Date());
        }
        album.setScore(0);
        albumDAO.insertAlbum(album);
        return s;
    }

    @Override
    @Transactional
    public String modifyAlbum(Album album, HttpSession session) {
        albumDAO.updateAlbum(album);
        if(session!=null){
            Album selectedAlbum = albumDAO.selectById(album.getId());
            if(!selectedAlbum.getCover().equals(album.getCover())){
                myUtil.deleteFile("uploadImg",selectedAlbum.getCover(),session);
            }
        }
        return album.getId();
    }

    @Override
    @Transactional
    public void removeAlbum(String albumId, HttpSession session) {
        myUtil.remove(AlbumDAO.class,albumId,Album.class,"getCover","uploadImg",session);
    }
}
