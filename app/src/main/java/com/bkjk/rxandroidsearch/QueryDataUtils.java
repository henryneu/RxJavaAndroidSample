package com.bkjk.rxandroidsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhenhua on 2018/4/24.
 */

public class QueryDataUtils {

    private List<String> mQueryDataList;

    public QueryDataUtils(List<String> mQueryDataList) {
        this.mQueryDataList = mQueryDataList;
    }

    /**
     * 根据输入字符查询并返回结果列表
     * @param inputQuery
     * @return
     */
    public List<String> search(String inputQuery) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputQuery = inputQuery.toLowerCase();
        // 查询到的返回结果值
        List<String> result = new ArrayList<>();
        for (int i = 0; i < mQueryDataList.size(); i++) {
            if (mQueryDataList.get(i).toLowerCase().contains(inputQuery)) {
                result.add(mQueryDataList.get(i));
            }
        }
        return result;
    }
}
