package com.dhoomilbsheta.pagingnews.vo;

import java.util.List;

import androidx.room.Entity;

public class Feed {
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
