package com.sanqiu.loro.applocktest.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by loro on 2018/4/9.
 */

public class RateBase implements Serializable{

    private String base;
    private String date;
    private Map<String, Float> rates = new HashMap<>();

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }
}
