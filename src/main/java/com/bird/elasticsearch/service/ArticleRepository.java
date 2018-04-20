package com.bird.elasticsearch.service;

import com.bird.elasticsearch.beans.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;
public interface ArticleRepository extends ElasticsearchRepository<Article,Integer>{


}
