package com.bird.mybatis.service;

import com.bird.mybatis.dao.PopupActivityMapper;
import com.bird.mybatis.model.PopupActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService{

    @Autowired
    private PopupActivityMapper activityMapper;

    @Override
    public List<PopupActivity> getActivity(Map<String, Object> map) {
        PopupActivity currentActivityPopup = activityMapper.getCurrentActivityPopup(map);
        return Collections.singletonList(currentActivityPopup);
    }
}
