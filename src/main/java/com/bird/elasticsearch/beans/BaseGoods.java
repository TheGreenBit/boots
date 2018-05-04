package com.bird.elasticsearch.beans;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class BaseGoods implements Serializable{
    protected Long collectionId;
    protected Integer brow;
    protected Boolean cart;

    protected Integer shopId;
    protected String shopName;
    protected String shopImage;

    protected Integer shopType;

    protected Integer countyId;
    //屏蔽信息
    protected Integer goodsSysTypeId;
    protected String typeName;
    protected String brandName;
    protected String adaptBrandName;
    protected String cityName;
    protected Integer brandId;
    protected Integer goodsStyle;

    protected String brandCode;
    protected String brandNO;
    protected Integer quality;

    protected String warrantyMail;
    protected String warrantyYear;

    protected String carType;
    protected String finance;
    protected String fineness;
    protected Boolean allCarType;


    protected List<String> carTypes=new ArrayList<>();

    protected List<Long> carTypeIds=new ArrayList<>();

    @Id
    protected Long goodsId;
    protected String imageUrl;
    protected Double price;
    protected String goodsName;
    protected Integer state;

    public void addCarInfo(CarType ct) {
        carTypes.add(ct.getCarTypeName());
        carTypeIds.add(ct.getCarTypeId());
        this.brandName = ct.getBrandName();
    }
}
