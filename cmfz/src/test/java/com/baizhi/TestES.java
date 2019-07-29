package com.baizhi;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository2;
import com.baizhi.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestES {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository2 articleRepository2;
    @Test
    public void testSave(){
        Map<String, Object> map = articleService.queryAll(1, 10000);
        List<Article> rows = (List<Article>)map.get("rows");
        for (Article row : rows) {
            articleRepository2.save(row);
        }
    }
}
