package com.bird.elasticsearch.repository;

import com.bird.elasticsearch.beans.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository extends ElasticsearchRepository<Goods,Long>{
}
