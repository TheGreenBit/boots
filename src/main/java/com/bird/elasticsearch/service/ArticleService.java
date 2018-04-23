package com.bird.elasticsearch.service;

import com.bird.elasticsearch.beans.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    void publishArticle(Article article) ;

    void updateArticle(Article article);

    void deleteArticle(Integer articleId);

    List<Article> getArticles(Map<String,Object> map);

    Article getArticle(Integer articleId);


}
