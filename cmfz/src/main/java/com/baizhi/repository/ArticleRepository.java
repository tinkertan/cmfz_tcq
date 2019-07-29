package com.baizhi.repository;

import com.baizhi.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRepository {
    List<Article> queryByContionAndPageAndHighLight(String contion,Integer page,Integer pageSize);
    Integer queryCount(String contion);
}
