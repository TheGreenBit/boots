package com.bird.mybatis.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "b_popup_activity")
public class PopupActivity {
    @Id
    private Integer popupId;

    private String button;

    private String url;

    private String imageUrl;

    private byte state;

    private short times;

    private String content;

    private String activityName;

    private String title;

    /**
     * @return popupId
     */
    public Integer getPopupId() {
        return popupId;
    }

    /**
     * @param popupId
     */
    public void setPopupId(Integer popupId) {
        this.popupId = popupId;
    }

    /**
     * @return button
     */
    public String getButton() {
        return button;
    }

    /**
     * @param button
     */
    public void setButton(String button) {
        this.button = button;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(byte state) {
        this.state = state;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public short getTimes() {
        return times;
    }

    public void setTimes(short times) {
        this.times = times;
    }
}
