package com.bkjk.rxjavasample.utils;

import com.bkjk.rxjavasample.model.BeautifulBean;
import com.bkjk.rxjavasample.model.BeautifulMapBean;
import com.bkjk.rxjavasample.model.BeautifulResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Created by zhouzhenhua on 2018/5/2.
 */

public class BeautifulResultToMapBeanMapper implements Function<BeautifulResult, List<BeautifulMapBean>> {

    private static BeautifulResultToMapBeanMapper INSTANCE = new BeautifulResultToMapBeanMapper();

    private BeautifulResultToMapBeanMapper() {
    }

    public static BeautifulResultToMapBeanMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<BeautifulMapBean> apply(BeautifulResult beautifulResult) {
        // 获取待转换的资源列表
        List<BeautifulBean> beautifulBeanList = beautifulResult.beautifulBeanList;
        // 新建转换后的资源列表并返回
        List<BeautifulMapBean> beautifulMapBeanList = new ArrayList<>(beautifulBeanList.size());
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        for (BeautifulBean beautifulBean : beautifulBeanList) {
            BeautifulMapBean beautifulMapBean = new BeautifulMapBean();
            try {
                Date date = inputDateFormat.parse(beautifulBean.createTime);
                beautifulMapBean.imageDescription = outputDateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                beautifulMapBean.imageDescription = "unknown date";
            }
            beautifulMapBean.imageUrl = beautifulBean.url;
            beautifulMapBeanList.add(beautifulMapBean);
        }
        return beautifulMapBeanList;
    }
}
