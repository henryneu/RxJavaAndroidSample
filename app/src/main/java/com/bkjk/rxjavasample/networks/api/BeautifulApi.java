package com.bkjk.rxjavasample.networks.api;

import com.bkjk.rxjavasample.model.BeautifulResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhouzhenhua on 2018/4/28.
 */

public interface BeautifulApi {

//    @GET("search")
//    Observable<List<String>> search(@Query("query") String query);

    @GET("data/福利/{number}/{page}")
    Observable<BeautifulResult> search(@Path("number") int number, @Path("page") int page);
}
