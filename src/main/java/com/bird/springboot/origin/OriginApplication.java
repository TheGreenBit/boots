package com.bird.springboot.origin;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Arrays;

@ComponentScan(value = {"com.bird"})
@EnableElasticsearchRepositories(basePackages = {"com.bird.elasticsearch"})
@SpringBootApplication
@EnableCaching
public class OriginApplication {

	public static void main(String[] args) {
        System.out.println("begin ..................................................");
		SpringApplication.run(OriginApplication.class, args);
        System.out.println("end ..................................................");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
	   return args -> {
           Arrays.stream(applicationContext.getBeanDefinitionNames()).sorted().forEach((a)->{System.out.println(a);});
       };
    }
}
