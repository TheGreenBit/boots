package com.bird.elasticsearch.service;

import com.bird.elasticsearch.beans.Article;
import com.bird.elasticsearch.repository.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    @Override
    public void publishArticle(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void updateArticle(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Integer articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public List<Article> getArticles(Map<String, Object> map) {
        Iterable<Article> all = articleRepository.findAll();
        List<Article> consumer = new ArrayList<>();
        all.forEach((a)->consumer.add(a));
        return consumer;
    }

    @Override
    public Article getArticle(Integer articleId) {
        Optional<Article> byId = articleRepository.findById(articleId);
        if(byId.isPresent()) {
            return byId.get();
        }
        return new Article();
    }


}
