package com.bird.springboot.controller;

import com.bird.config.Bird;
import com.bird.config.ConfigDolphin;
import com.bird.config.NamedBean;
import com.bird.elasticsearch.beans.Article;
import com.bird.mybatis.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;


@RestController
public class HelloWorldController {

    @Autowired
    private Bird bird;

    @Autowired
    private ConfigDolphin configDolphin;

    @Autowired
    private ActivityService activityService;

    @GetMapping("activity")
    public String beanArticle(@RequestParam("state")Integer state) {
        return activityService.getActivity(Collections.singletonMap("state",(Object) state)).toString();
    }

    @Autowired
    private NamedBean namedBean;


    @GetMapping("hello")
    public String helloworld() {
        return "Hello World!";
    }

    @GetMapping("bird")
    public String bird() {
        return bird.toString();
    }

    @GetMapping("dolphin")
    public String dolphin() {
        return configDolphin.toString();
    }
}
