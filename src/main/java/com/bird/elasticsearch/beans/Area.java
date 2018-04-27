package com.bird.elasticsearch.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/21.
 */

@SuppressWarnings("serial")
public
@Data
class Area implements Serializable {

    public static final Area EMPTY = new Area(0L,0L,0L,"","","");

    private Long provinceId;

    private Long cityId;

    private Long countyId;

    private String province;

    private String city;

    private String county;

    public Area(Long cityId) {
        this.cityId = cityId;
    }


    public Area(Long provinceId, Long cityId, Long countyId, String province, String city, String county) {
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.countyId = countyId;
        this.province = province;
        this.city = city;
        this.county = county;
    }

    public Area() {
    }



    public String provinceAndCity() {
        return province + city;
    }

    public String cityCounty() {
        return city + county;
    }

    public String provinceCityCountry() {
        return province + city + county;
    }


    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId == null ? 0L : countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
