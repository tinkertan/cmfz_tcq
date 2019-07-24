package com.baizhi.service;

import com.baizhi.entity.Chapter;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    Map<String,Object> queryAll(Integer pageNum,Integer pageSize,String albumId);
    String addChapter(Chapter chapter);
    void removeChapter(String id, HttpSession session);
    void updateDownPath(Chapter chapter);
}
