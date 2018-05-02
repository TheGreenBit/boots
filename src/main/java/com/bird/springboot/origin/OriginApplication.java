package com.bird.springboot.origin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.map.repository.config.EnableMapRepositories;

import java.util.Arrays;

@ComponentScan(value = {"com.bird"})
@EnableElasticsearchRepositories(basePackages = {"com.bird.elasticsearch.repository"})
@MapperScan(basePackages = {"com.bird.mybatis.dao"})
@SpringBootApplication
@EnableCaching
public class OriginApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(OriginApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> {
            Arrays.stream(applicationContext.getBeanDefinitionNames()).sorted().forEach((a) -> System.out.println(a));
        };
    }


}
