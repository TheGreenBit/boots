package com.bird.elasticsearch.service;

import com.bird.elasticsearch.beans.Goods;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface GoodsSearchService {

    @Async
    void doIndex();

}
