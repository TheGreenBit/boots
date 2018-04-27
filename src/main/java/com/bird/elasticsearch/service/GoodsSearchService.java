package com.bird.elasticsearch.service;

import com.bird.elasticsearch.beans.Goods;
import com.bird.elasticsearch.beans.GoodsQuery;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface GoodsSearchService {

    @Async
    void doIndex();

    List<Goods> search(GoodsQuery goodsQuery);

}
