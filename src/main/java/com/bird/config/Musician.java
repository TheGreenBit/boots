package com.bird.config;

import lombok.Data;

@Data
public class Musician {
    private String name;
    private String location;
    private int age;

    public Musician(String name, String location, int age) {
        this.name = name;
        this.location = location;
        this.age = age;
    }

    public Musician() {
    }
}
