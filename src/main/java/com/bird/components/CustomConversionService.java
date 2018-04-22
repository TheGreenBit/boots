package com.bird.components;

import com.bird.config.Bird;
import com.bird.elasticsearch.beans.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.internal.util.ImmutableSet;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomConversionService extends ConversionServiceFactoryBean{

    @Override
    public void afterPropertiesSet() {
        addCustom();
        super.afterPropertiesSet();
    }

    private void addCustom() {
        super.setConverters(ImmutableSet.of(new CC()));
    }

    public class CC implements Converter<String,Article> {

        private AtomicInteger ai = new AtomicInteger(3);

        @Override
        public Article convert(String s) {
            Article a  = new Article();
            a.setArticleId((long) ai.getAndIncrement());
            a.setTitle(s);
            a.setArea("白庙村");
            a.setContent("bird now here!");

            System.out.println("custom converter going...");
            return a;
        }
    }
}
