package com.bird.elasticsearch.beans;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(indexName = "e_goods",type = "goods")
public class GoodsData extends BaseGoods implements Serializable{

    private GeoPoint location;



   public static GoodsData of(Goods goods) {
       GoodsData goodsData = new GoodsData();
       BeanUtils.copyProperties(goods,goodsData);
       goodsData.location= new GeoPoint(goods.getLatitude().doubleValue(),goods.getLongitude().doubleValue());
       return goodsData;
   }
}
