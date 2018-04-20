package com.bird.elasticsearch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElasticsearchController {

    @GetMapping("elasticsearch")
    public void article(@RequestParam("articleId")Integer articleId){


    }
}
