package com.bird.elasticsearch.service;

import com.bird.elasticsearch.beans.GoodsData;
import com.bird.elasticsearch.beans.GoodsQuery;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface GoodsSearchService {

    @Async
    void doIndex();

    @Async
    void doIndexQuickly();

    @Async
    void doIndexDeque();

    List<GoodsData> search(GoodsQuery goodsQuery);

}
