package com.sanqiu.loro.applocktest.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.sanqiu.loro.applocktest.utils.RateComparator;
import com.sanqiu.loro.applocktest.utils.SharePrefUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by loro on 2018/4/9.
 */

public class Rate implements Serializable {
    public final static String SHAREPRE_KEY_CACHE_RATE_LIST = "key_rate_list";

    private int rate_index = 100;
    private String rate_name = "";
    private float rate_exchange = 0.0f;

    public int getRate_index() {
        return rate_index;
    }

    public void setRate_index(int rate_index) {
        this.rate_index = rate_index;
    }

    public String getRate_name() {
        return rate_name;
    }

    public void setRate_name(String rate_name) {
        this.rate_name = rate_name;
    }

    public float getRate_exchange() {
        return rate_exchange;
    }

    public void setRate_exchange(float rate_exchange) {
        this.rate_exchange = rate_exchange;
    }

    public static List<Rate> getListByMapOrder(Map<String, Float> maps, Rate nowRate) {
        List<Rate> lists = new ArrayList<>();
        List<Rate> listCaches = new ArrayList<>();
        String cacheJson = SharePrefUtil.getString(SHAREPRE_KEY_CACHE_RATE_LIST, "");
        if (!TextUtils.isEmpty(cacheJson)) {
            try {
                listCaches = getListCaches(cacheJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null != maps) {
            if (null != maps) {
                maps.put(nowRate.getRate_name(), nowRate.getRate_exchange());
            }
            if (null != listCaches) {
                lists = getListByMap(maps, listCaches);
            } else {
                lists = getListByMap(maps);
            }
        }

        saveRateCaches(lists);
        return lists;
    }

    public static List<Rate> getListByMap(Map<String, Float> maps) {
        List<Rate> lists = new ArrayList<>();
        int index = 0;
        if (null != maps) {
            Iterator it = maps.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next().toString();
                Rate rate = new Rate();
                rate.setRate_name(key);
                rate.setRate_exchange(maps.get(key));
                rate.setRate_index(index);
                lists.add(rate);

                index++;
            }
        }
        return lists;
    }

    public static List<Rate> getListByMap(Map<String, Float> maps, List<Rate> listCaches) {
        List<Rate> lists = new ArrayList<>();
        int index = 0;
        if (null != maps) {
            Iterator it = maps.keySet().iterator();
            while (it.hasNext()) {
                Rate rate = new Rate();
                String key = it.next().toString();
                rate.setRate_name(key);
                rate.setRate_exchange(maps.get(key));
                rate.setRate_index(index);
                if (null != listCaches && listCaches.size() > 0) {
                    for (Rate rateCache : listCaches) {
                        if (key.equals(rateCache.getRate_name())) {
                            rate.setRate_index(rateCache.getRate_index());
                            break;
                        }
                    }
                }
                lists.add(rate);

                index++;
            }
        }
        return lists;
    }

    public static void saveRateCaches(List<Rate> listCaches) {
        if (null != listCaches && listCaches.size() > 0) {
            try {
                RateComparator comparator = new RateComparator();
                Collections.sort(listCaches, comparator);
                String jsonCache = JSON.toJSONString(listCaches);
                SharePrefUtil.putString(SHAREPRE_KEY_CACHE_RATE_LIST, jsonCache);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Rate> getListCaches(String jsonCache) {
        if (!TextUtils.isEmpty(jsonCache)) {
            try {
                List<Rate> listCaches = JSON.parseArray(jsonCache, Rate.class);
                return listCaches;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void chageOrderAndSave(List<Rate> lists) {
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setRate_index(i);
        }
        saveRateCaches(lists);
    }
}
