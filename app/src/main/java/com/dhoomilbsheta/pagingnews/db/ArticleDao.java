package com.dhoomilbsheta.pagingnews.db;

import com.dhoomilbsheta.pagingnews.vo.Article;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Article> articles);

    @Query("SELECT count(url) FROM article")
    int getCount();

    @Query("SELECT * FROM article")
    DataSource.Factory<Integer, Article> get();
}
