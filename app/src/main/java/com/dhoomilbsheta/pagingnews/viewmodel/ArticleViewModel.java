package com.dhoomilbsheta.pagingnews.viewmodel;

import android.app.Application;

import com.dhoomilbsheta.pagingnews.data.ArticleRepository;
import com.dhoomilbsheta.pagingnews.data.NetworkState;
import com.dhoomilbsheta.pagingnews.vo.Article;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class ArticleViewModel extends AndroidViewModel {
    private LiveData<PagedList<Article>> pagedArticles;
    private LiveData<NetworkState> networkState;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        ArticleRepository articleRepo = new ArticleRepository(application);
        pagedArticles = articleRepo.getArticles();
        networkState = articleRepo.getNetworkState();
    }

    public LiveData<PagedList<Article>> getPagedArticles() {
        return pagedArticles;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
