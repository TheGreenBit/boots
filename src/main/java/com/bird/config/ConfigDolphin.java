package com.bird.config;

import com.bird.elasticsearch.beans.Article;
import lombok.Data;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "dolphin")
@Configuration
public class ConfigDolphin implements Serializable{
    private String name;
    private int age;
    private String gender;
    private double weight;
    private double height;
    private Object secret;

    private Bird bird;

    ApplicationContext applicationContext;

    @Resource
    public void setBird(Bird bird) {
        this.bird = bird;
    }

    @Resource
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    };

    @Bean
    public Article article(Bird bird) {
        System.out.println("go ..........................................."+bird.toString());
        Article article = new Article();
        article.setArticleId(33L);
        article.setContent(bird.getSecret().toString());
        return article;
    }
}
