package com.bird.springboot.tbeans;

import com.bird.elasticsearch.beans.Article;
import lombok.Data;

@Data
public class ArticleHolder {
    private Integer userId;

    private Article article;
}
