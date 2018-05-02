package com.bkjk.rxjavasample.networks;

import com.bkjk.rxjavasample.networks.api.BeautifulApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhouzhenhua on 2018/4/28.
 */

public class NetWorks {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private static Converter.Factory mGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory mRxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static BeautifulApi mBeautifulApi;

    /**
     * 获取
     */
    public static BeautifulApi getBeautifulApi() {
        if (mBeautifulApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl("http://gank.io/api")
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxJava2CallAdapterFactory)
                    .build();
            mBeautifulApi = retrofit.create(BeautifulApi.class);
        }
        return mBeautifulApi;
    }
}
