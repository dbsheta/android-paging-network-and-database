package com.dhoomilbsheta.pagingnews.data;

import android.util.Log;

import com.dhoomilbsheta.pagingnews.api.ApiConstants;
import com.dhoomilbsheta.pagingnews.api.NewsService;
import com.dhoomilbsheta.pagingnews.db.ArticleDao;
import com.dhoomilbsheta.pagingnews.vo.Article;
import com.dhoomilbsheta.pagingnews.vo.Feed;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsBoundaryCallback extends PagedList.BoundaryCallback<Article> implements Callback<Feed> {
    private NewsService newsService;
    private ArticleDao articleDao;
    private Executor executor;
    private MutableLiveData<NetworkState> networkState;

    NewsBoundaryCallback(NewsService newsService, ArticleDao articleDao) {
        this.newsService = newsService;
        this.articleDao = articleDao;
        this.executor = Executors.newFixedThreadPool(2);
        networkState = new MutableLiveData<>();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        Log.d("BOUNDARY", "Called on zero items");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        executor.execute(() -> fetchNews(dateStr));
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Article itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Log.d("BOUNDARY", "Called at end");
        String lastItemDate = itemAtEnd.getPublishedAt();
        executor.execute(() -> fetchNews(lastItemDate));
    }

    MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    private void fetchNews(String date) {
        Log.d("NEWS", "making request to fetch news upto date: " + date);
        networkState.postValue(NetworkState.LOADING);
        newsService.fetchFeed(ApiConstants.API_KEY, ApiConstants.QUERY, ApiConstants.PAGE_SIZE, ApiConstants.SORT_BY, date)
                .enqueue(this);
    }

    @Override
    public void onResponse(Call<Feed> call, Response<Feed> response) {
        if (response.isSuccessful()) {
            Log.d("NEWS", "Fetched " + response.body().getArticles().size());
            networkState.postValue(NetworkState.LOADED);
            executor.execute(() -> {
                articleDao.insert(response.body().getArticles());
                int count = articleDao.getCount();
                Log.d("NEWS", "Inserted in db: " + count);
            });
        } else {
            Log.e("NEWS ERROR", response.message());
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
        }
    }

    @Override
    public void onFailure(Call<Feed> call, Throwable t) {
        Log.e("NEWS FAILURE", t.getMessage());
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
    }
}
