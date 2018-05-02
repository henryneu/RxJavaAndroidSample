package com.bkjk.rxjavasample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhouzhenhua on 2018/5/2.
 */

public class BeautifulResult {
    @SerializedName("results")
    public List<BeautifulBean> beautifulBeanList;
}
