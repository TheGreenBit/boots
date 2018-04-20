package com.bird.jdbc.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ChatPhrase implements Serializable {

    private Integer phraseId;

    private String tip;

    private Integer userId;

    private Boolean canShow;

    private Date updateTime;
}
