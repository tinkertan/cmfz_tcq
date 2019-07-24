package com.baizhi.service;

import com.baizhi.entity.Article;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ArticleService {
    Map<String,Object> queryAll(Integer pageNum,Integer pageSize);
    Article queryById(String id);

    String addArticle(Article article);
    String modifyArticle(Article article, HttpSession session);
    void removeArticle(String id,HttpSession session);
}
