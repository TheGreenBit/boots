package com.bird.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class RequestApplicationListener implements ApplicationListener<RequestHandledEvent>{


    @Override
    public void onApplicationEvent(RequestHandledEvent requestHandledEvent) {
        System.out.println("one request be handled!......execute time: "+requestHandledEvent.getProcessingTimeMillis());
        System.out.println(requestHandledEvent.getSource());
        System.out.println("event is ...."+requestHandledEvent.getDescription());
        System.out.println(requestHandledEvent.getFailureCause());
        if(requestHandledEvent.wasFailure()) {
            System.out.println("execute failure ,cause was:");

        }else {
            System.out.println("event is success...."+requestHandledEvent.getShortDescription());
        }
    }
}
