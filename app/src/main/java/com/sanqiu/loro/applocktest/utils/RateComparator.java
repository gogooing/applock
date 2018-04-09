package com.sanqiu.loro.applocktest.utils;

import com.sanqiu.loro.applocktest.model.Rate;

import java.util.Comparator;

/**
 * Created by msdnet on 2018/4/9.
 */

public class RateComparator implements Comparator<Rate> {
    @Override
    public int compare(Rate t1, Rate t2) {
        return ((Integer) (t1).getRate_index())
                .compareTo((Integer) (t2).getRate_index());
    }
}
