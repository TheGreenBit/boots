package com.bird.elasticsearch.controller;

import com.bird.elasticsearch.beans.GoodsQuery;
import com.bird.elasticsearch.service.GoodsSearchService;
import com.bird.utils.ExtractParameterUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class GoodsController {

    @Autowired
    private GoodsSearchService goodsSearchService;

    @Autowired
    private ObjectMapper objectMapper;



    //@GetMapping("index/goods")
    public String indexGoods() {
        goodsSearchService.doIndex();
        return "going index goods";
    }

    //@GetMapping("index/quickly")
    public String indexGoodsQuickly() {
        goodsSearchService.doIndexQuickly();
        return "going index version 2 goods";
    }

    @GetMapping("index/deque")
    public String indexGoodsDeque() {
        goodsSearchService.doIndexDeque();
        return "going index version 2 goods";
    }

    @GetMapping("goods/query")
    public String goodsQuery(HttpServletRequest request) throws Exception {
        GoodsQuery goodsQuery = ExtractParameterUtils.extractFromHttpRequest(GoodsQuery.class, request);
        return objectMapper.writeValueAsString(goodsSearchService.search(goodsQuery));
    }



}
