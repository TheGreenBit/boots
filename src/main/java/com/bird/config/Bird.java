package com.bird.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Data
@Component
public class Bird {
    @Value("${bird.name}")
    private String name;
    @Value("${bird.age}")
    private int age;
    @Value("${bird.gender}")
    private String gender;
    @Value("${bird.weight}")
    private double weight;
    @Value("${bird.height}")
    private double height;
    @Value("${bird.secret}")
    private Object secret;
}
