package com.baizhi.repository;

import com.baizhi.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ArticleRepositoryImpl implements ArticleRepository {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Override
    public List<Article> queryByContionAndPageAndHighLight(String contion, Integer page, Integer pageSize) {
        HighlightBuilder.Field highlightBuilder = new HighlightBuilder.Field("*").preTags("<span style='color:red'>").postTags("</span>").requireFieldMatch(false);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(page-1, pageSize))
                .withQuery(QueryBuilders.multiMatchQuery(contion, "title", "content", "guruName"))
                .withHighlightFields(highlightBuilder).build();
        log.info("articleRepository queryByContionAndPageAndHighLight param:{} page:{} pageSize:{}",contion,page,pageSize);
        AggregatedPage<Article> queryForPage = elasticsearchTemplate.queryForPage(nativeSearchQuery, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                SearchHit[] hits1 = hits.getHits();
                List<Article> articles = new ArrayList<>();
                for (SearchHit searchHit : hits1) {
                    Article article = new Article();
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    article.setId(sourceAsMap.get("id").toString());
                    article.setContent(sourceAsMap.get("content").toString());
                    String publishTime = sourceAsMap.get("publishTime").toString();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date parse = dateFormat.parse(publishTime);
                        article.setPublishTime(parse);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    article.setGuruId(sourceAsMap.get("guruId").toString());
                    article.setTitle(sourceAsMap.get("title").toString());
                    article.setGuruName(sourceAsMap.get("guruName").toString());
                    Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                    if (highlightFields.get("title")!=null){
                        article.setTitle(highlightFields.get("title").getFragments()[0].toString());
                    }
                    if(highlightFields.get("content")!=null){
                        article.setContent(highlightFields.get("content").getFragments()[0].toString());
                    }
                    if (highlightFields.get("guruName")!=null){
                        article.setGuruName(highlightFields.get("guruName").getFragments()[0].toString());
                    }
                    //log.info("article {}",article);
                    articles.add(article);
                }
                log.info("articles size {}",articles.size());
                return new AggregatedPageImpl<T>((List<T>)articles);
            }
        });
        return queryForPage.getContent();
    }

    @Override
    public Integer queryCount(String contion) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(contion, "title", "content", "guruName"))
                .build();
        AggregatedPage<Article> queryForPage = elasticsearchTemplate.queryForPage(nativeSearchQuery, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                SearchHit[] hits1 = hits.getHits();
                List<Article> articles = new ArrayList<>();
                for (SearchHit searchHit : hits1) {
                    Article article = new Article();
                    articles.add(article);
                }
                return new AggregatedPageImpl<T>((List<T>)articles);
            }
        });
        return queryForPage.getContent().size();
    }
}
