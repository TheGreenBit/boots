package com.bird.elasticsearch.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class EpcPart implements Serializable{
    private Integer partsId;
    private String partsName;
    private Long posId;
    private String posName;
    private Integer commonId;
    private String commonName;
    private String partsCode;
    private String oe;
    private String memo;
    private String num;
    private String price;
    private String salePrice;
    private String pic;
    private String remark;//补充说明
    private Integer brandId;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EpcPart epcParts = (EpcPart) o;

        return (oe != null ? oe.equals(epcParts.oe) : epcParts.oe != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return  31 * result + (oe != null ? oe.hashCode() : 0);
    }
}
