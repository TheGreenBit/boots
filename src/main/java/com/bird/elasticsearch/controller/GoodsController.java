package com.bird.elasticsearch.controller;

import com.bird.elasticsearch.service.GoodsSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class GoodsController {

    @Autowired
    private GoodsSearchService goodsSearchService;

    private ObjectMapper objectMapper = new ObjectMapper();



    @GetMapping("index/goods")
    public String indexGoods() {
        goodsSearchService.doIndex();
        return "going index goods";
    }



}
