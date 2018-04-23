package com.bird.elasticsearch.controller;

import com.bird.elasticsearch.beans.Article;
import com.bird.elasticsearch.service.ArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("article")
    public String article(@RequestParam("articleId")Integer articleId) throws JsonProcessingException {
        Article article = articleService.getArticle(articleId);
        return objectMapper.writeValueAsString(article);
    }

    @PostMapping("publish")
    public void publish(@RequestBody Article article) {
        articleService.publishArticle(article);
    }

    @PostMapping("delete")
    public void publish(@RequestParam("articleId") Integer articleId) {
        articleService.deleteArticle(articleId);
    }

    @GetMapping("articles")
    public String articles () throws JsonProcessingException {
        return objectMapper.writeValueAsString(articleService.getArticles(null));
    }

}
