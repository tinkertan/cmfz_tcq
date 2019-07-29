package com.baizhi.service;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository;
import com.baizhi.repository.ArticleRepository2;
import com.baizhi.util.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleRepository2 articleRepository2;

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
        articleRepository2.save(article);
        return s;
    }

    @Override
    public String modifyArticle(Article article, HttpSession session) {
        Article selectedArticle = articleDAO.selectById(article.getId());
        articleDAO.updateArticle(article);
        articleRepository2.save(article);
        if(!selectedArticle.getId().equals(article.getId())){
            myUtil.deleteFile("uploadFile",selectedArticle.getContent(),session);
        }
        return article.getId();
    }

    @Override
    public void removeArticle(String id, HttpSession session) {
        myUtil.remove(ArticleDAO.class,id,Article.class,"getContent","uploadFile",session);
        articleRepository2.deleteById(id);
    }

    @Override
    public Map<String, Object> queryLike(String contion, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<Article> list = articleRepository.queryByContionAndPageAndHighLight(contion, page, pageSize);
        Integer count = articleRepository.queryCount(contion);
        Integer total = (count%pageSize==0)?(count/pageSize):(count/pageSize+1);
        map.put("pageNum",page);
        map.put("rows",list);
        map.put("pageTotal",total);
        map.put("countNum",count);
        //log.info("map page {} rows {} total {} records{}",page,list,total,count);
        return map;
    }
}
