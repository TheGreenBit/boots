package com.bird.elasticsearch.repository;

import com.bird.elasticsearch.beans.GoodsData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository extends ElasticsearchRepository<GoodsData,Long>{
}
