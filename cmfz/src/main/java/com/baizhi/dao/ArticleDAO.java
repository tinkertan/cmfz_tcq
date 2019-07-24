package com.baizhi.dao;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleDAO {
    List<Article> selectAll(Map map);
    Integer selectCount();
    Article selectById(String id);

    void insertArtivcle(Article artile);
    void updateArticle(Article article);
    void deleteById(String id);
}
