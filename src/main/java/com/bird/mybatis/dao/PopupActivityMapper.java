package com.bird.mybatis.dao;

import com.bird.mybatis.model.PopupActivity;

import java.util.Map;

public interface PopupActivityMapper {

    PopupActivity getCurrentActivityPopup(Map<String, Object> state);

    Integer checkIsJoin(Integer userId);

}
