package com.dhoomilbsheta.pagingnews.api;

import com.dhoomilbsheta.pagingnews.vo.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("/v2/everything")
    Call<Feed> fetchFeed(
            @Query("apiKey") String apiKey,
            @Query("q") String q,
            @Query("pageSize") int pageSize,
            @Query("sortBy") String sortBy,
            @Query("to") String to
    );

}
