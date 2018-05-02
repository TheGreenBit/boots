package com.bird.mybatis.service;


import com.bird.mybatis.model.PopupActivity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

     List<PopupActivity> getActivity(Map<String,Object> map);

}
