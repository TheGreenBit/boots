package com.bird.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "dolphin")
@Component
public class ConfigDolphin implements Serializable{
    private String name;
    private int age;
    private String gender;
    private double weight;
    private double height;
    private Object secret;
}
