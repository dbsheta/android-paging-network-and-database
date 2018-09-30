package com.dhoomilbsheta.pagingnews.data;

import android.app.Application;
import android.util.Log;

import com.dhoomilbsheta.pagingnews.api.ApiConstants;
import com.dhoomilbsheta.pagingnews.api.NewsService;
import com.dhoomilbsheta.pagingnews.api.ServiceGenerator;
import com.dhoomilbsheta.pagingnews.db.AppDatabase;
import com.dhoomilbsheta.pagingnews.db.ArticleDao;
import com.dhoomilbsheta.pagingnews.vo.Article;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class ArticleRepository {
    private static final int DB_PAGE_SIZE = 10;
    private ArticleDao articleDao;
    private NewsService newsService;
    private MutableLiveData<NetworkState> networkState;

    public ArticleRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        articleDao = appDatabase.articleDao();
        newsService = ServiceGenerator.createService(NewsService.class);
    }

    public LiveData<PagedList<Article>> getArticles() {
        DataSource.Factory<Integer, Article> dataSource = articleDao.get();
        NewsBoundaryCallback callback = new NewsBoundaryCallback(newsService, articleDao);
        networkState = callback.getNetworkState();

        return new LivePagedListBuilder<>(dataSource, DB_PAGE_SIZE)
                .setBoundaryCallback(callback)
                .build();
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
