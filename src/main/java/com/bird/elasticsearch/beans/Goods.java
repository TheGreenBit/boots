package com.bird.elasticsearch.beans;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(indexName = "e_goods_index",type = "goods")
public class Goods implements Serializable{
    private Long collectionId;
    private Integer brow;
    private Boolean cart;

    private Integer shopId;
    private String shopName;
    private String shopImage;

    private Integer shopType;

    private Integer countyId;
    //屏蔽信息
    private Integer goodsSysTypeId;
    private String typeName;
    private String brandName;
    private String adaptBrandName;
    private String cityName;
    private Integer brandId;
    private Integer goodsStyle;

    private String brandCode;
    private String brandNO;
    private Integer quality;

    private String warrantyMail;
    private String warrantyYear;

    private String carType;
    private String finance;
    private String fineness;
    private Boolean allCarType;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private List<String> carTypes=new ArrayList<>();

    private List<Long> carTypeIds=new ArrayList<>();

    @Id
    private Long goodsId;
    private String imageUrl;
    private Double price;
    private String goodsName;
    private Integer state;

    public void addCarInfo(CarType ct) {
        carTypes.add(ct.getCarTypeName());
        carTypeIds.add(ct.getCarTypeId());
        this.brandName = ct.getBrandName();
    }
}
