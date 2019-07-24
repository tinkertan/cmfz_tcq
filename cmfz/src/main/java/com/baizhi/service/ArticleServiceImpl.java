package com.baizhi.service;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private MyUtil myUtil;

    @Override
    public Map<String, Object> queryAll(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = myUtil.queryAllForJqgrid(pageNum, pageSize, ArticleDAO.class,null);
        return map;
    }

    @Override
    public Article queryById(String id) {
        return articleDAO.selectById(id);
    }

    @Override
    public String addArticle(Article article) {
        String s = UUID.randomUUID().toString();
        article.setId(s);
        if(article.getPublishTime()==null){
            article.setPublishTime(new Date());
        }
        articleDAO.insertArtivcle(article);
        return s;
    }

    @Override
    public String modifyArticle(Article article, HttpSession session) {
        Article selectedArticle = articleDAO.selectById(article.getId());
        articleDAO.updateArticle(article);
        if(!selectedArticle.getId().equals(article.getId())){
            myUtil.deleteFile("uploadFile",selectedArticle.getContent(),session);
        }
        return article.getId();
    }

    @Override
    public void removeArticle(String id, HttpSession session) {
        myUtil.remove(ArticleDAO.class,id,Article.class,"getContent","uploadFile",session);
    }
}
