package com.dhoomilbsheta.pagingnews.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.dhoomilbsheta.pagingnews.R;
import com.dhoomilbsheta.pagingnews.vo.Article;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ArticleViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView desc;
    private final TextView date;

    ArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.news_title);
        desc = itemView.findViewById(R.id.news_desc);
        date = itemView.findViewById(R.id.news_date);
    }

    void bind(Article article) {
        title.setText(article.getTitle());
        desc.setText(article.getDescription());
        date.setText(article.getPublishedAt());
    }
}
