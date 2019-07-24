package com.baizhi.dao;

import com.baizhi.entity.Carousel;
import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterDAO{
    /*查询所有带分页*/
    List<Chapter> selectAll(Map map);
    void updateChapterDownloadPath(Chapter chapter);
    Chapter selectById(String id);
    Integer selectCount(String albumId);
    /*添加*/
    void insert(Chapter t);
    /*删除*/
    void deleteById(String id);
}
