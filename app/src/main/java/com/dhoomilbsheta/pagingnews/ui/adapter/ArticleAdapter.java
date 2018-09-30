package com.dhoomilbsheta.pagingnews.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhoomilbsheta.pagingnews.R;
import com.dhoomilbsheta.pagingnews.data.NetworkState;
import com.dhoomilbsheta.pagingnews.vo.Article;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends PagedListAdapter<Article, RecyclerView.ViewHolder> {
    private final LayoutInflater inflater;
    private NetworkState networkState;

    public ArticleAdapter(Context context) {
        super(Article.DIFF_CALLBACK);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case R.layout.item_article:
                view = inflater.inflate(R.layout.item_article, parent, false);
                return new ArticleViewHolder(view);
            case R.layout.item_network_state:
                view = inflater.inflate(R.layout.item_network_state, parent, false);
                return new NetworkStateViewHolder(view);
            default:
                throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_article:
                Article article = getItem(position);
                if (article != null)
                    ((ArticleViewHolder) holder).bind(getItem(position));
                break;
            case R.layout.item_network_state:
                ((NetworkStateViewHolder) holder).bind(networkState);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.item_network_state;
        }
        return R.layout.item_article;
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
}
