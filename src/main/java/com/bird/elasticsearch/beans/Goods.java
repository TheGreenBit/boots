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
public class Goods extends BaseGoods implements Serializable{


    private BigDecimal latitude;
    private BigDecimal longitude;

    public BigDecimal getLatitude() {
        return latitude==null?new BigDecimal(34.452):latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude==null?new BigDecimal(118.555):longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
