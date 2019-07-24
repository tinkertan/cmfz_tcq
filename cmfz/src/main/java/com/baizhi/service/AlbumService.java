package com.baizhi.service;

import com.baizhi.entity.Album;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface AlbumService {
    /*查询所有带分页*/
    Map<String,Object> queryAll(Integer pageNum,Integer pageSize);
    /*查询单张专辑根据id*/
    Album queryByAlbumId(String albumId);
    /*添加一张专辑*/
    String addAlbum(Album album);
    /*修改一张专辑*/
    String modifyAlbum(Album album, HttpSession session);
    /*删除一张专辑*/
    void removeAlbum(String albumId,HttpSession session);
}
