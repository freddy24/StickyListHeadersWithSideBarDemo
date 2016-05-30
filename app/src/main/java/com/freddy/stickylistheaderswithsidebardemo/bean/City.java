package com.freddy.stickylistheaderswithsidebardemo.bean;

import java.io.Serializable;

/**
 * author:wjp
 * Description:
 * data: 2015/9/11
 * Copyright (c) 2014-2017 
 */
public class City implements Serializable {

    private String cityName;
    private String cityCode;
    private String sortLetters;

    public City(String cityName, String cityCode){
        this.cityName = cityName;
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
