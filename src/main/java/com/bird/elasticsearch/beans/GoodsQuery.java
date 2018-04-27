package com.bird.elasticsearch.beans;

import com.bird.elasticsearch.parse.Match;
import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.elasticsearch.annotations.Query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GoodsQuery implements Serializable {
    @Match
    private String goodsName;
    @Match
    private Integer cityId;
    @Match
    private Integer carTypeId;
    @Match
    private Double price;
    @Match
    private String shopName;
    @Match
    private String brandName;

    @Match
    private Integer brandId;

    public List<QueryBuilder> getQueryBuilders() {
        Field[] fields = this.getClass().getDeclaredFields();
        List<QueryBuilder> collect = Arrays.stream(fields).map(field -> queryBuilderFromField(field))
                .filter(queryBuilder -> queryBuilder!=null).collect(Collectors.toList());
        return collect;
    }

    private QueryBuilder queryBuilderFromField(Field field) {
        Match annotation = field.getAnnotation(Match.class);
        if(annotation!=null) {
            field.setAccessible(true);
            Object o = null;
            try {
                o = field.get(this);
                if(o!=null) {
                    return QueryBuilders.matchQuery(field.getName(), o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
