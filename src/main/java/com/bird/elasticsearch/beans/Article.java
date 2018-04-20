package com.bird.elasticsearch.beans;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Data
@Document(indexName = "article_index", type = "article")
public class Article implements Serializable{

    @Id
    private Long articleId;

    private Integer articleType;

    private Long userId;

    private String title;

    private String content;
    private String area;
    private String url;

    private Boolean hasPic;

    private Long zanCount;

    private Integer timegroup;
    private Integer state;
    private Integer wtId;

    private Long commentCount;

    private Date createTime;
}
