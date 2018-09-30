package com.dhoomilbsheta.pagingnews.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhoomilbsheta.pagingnews.R;
import com.dhoomilbsheta.pagingnews.vo.Article;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends PagedListAdapter<Article, ArticleAdapter.ArticleViewHolder> {
    private final LayoutInflater inflater;

    public ArticleAdapter(Context context) {
        super(Article.DIFF_CALLBACK);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_news_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = getItem(position);
        if (article != null) {
            holder.bind(article);
        }
    }

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
}
