package com.bird.config;

import lombok.Data;

@Data
public class Album {
    private String title;
    private Musician author;
    private long minutes;

    public Album() {
    }

    public Album(String title, Musician author, long minutes) {
        this.title = title;
        this.author = author;
        this.minutes = minutes;
    }
}
