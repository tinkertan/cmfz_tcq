package com.baizhi.dao;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumDAO {
    /*查询所有带分页*/
    List<Album> selectAll(Map map);
    /*查询记录的总条数*/
    Integer selectCount();
    /*查询单张专辑根据id*/
    Album selectById(String albumId);
    /*添加一张专辑*/
    void insertAlbum(Album album);
    /*修改一张专辑*/
    void updateAlbum(Album album);
    /*删除一张专辑*/
    void deleteById(String albumId);
}
