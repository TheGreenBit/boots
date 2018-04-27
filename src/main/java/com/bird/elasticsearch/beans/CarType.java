package com.bird.elasticsearch.beans;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/20.
 */
@SuppressWarnings("serial")
@Repository
@Data
public class CarType implements Serializable {

    // 自增ID
    private Long carTypeId;

    private Integer brandId;

    private Integer carSysId;

    private Long bcId;

    // 厂家
    private String manufactor;

    // 品牌
    private String brandName;

    private String carSysName;

    // 车型
    private String carTypeName;

    private Integer yearModel;
    private Integer level;
    private String levelInfo;

    private String carNO;

    private String engineType;

    private String transmType;

    private Long rawId;
    private Long MSD_NUM;
    private Boolean epc;
    private Boolean conf;



}
