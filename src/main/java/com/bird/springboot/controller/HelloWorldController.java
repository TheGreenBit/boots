package com.bird.springboot.controller;

import com.bird.config.Bird;
import com.bird.config.ConfigDolphin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

    @Autowired
    private Bird bird;

    @Autowired
    private ConfigDolphin configDolphin;

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
