package com.dhoomilbsheta.pagingnews.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhoomilbsheta.pagingnews.R;
import com.dhoomilbsheta.pagingnews.ui.adapter.ArticleAdapter;
import com.dhoomilbsheta.pagingnews.viewmodel.ArticleViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private ArticleViewModel articleViewModel;
    private ArticleAdapter articleAdapter;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        configureRecyclerView();
        configureOtherWidgets();
        observeNetworkState();
        observeNewsData();
    }

    private void configureOtherWidgets() {
        progressBar = findViewById(R.id.loading);
        textView = findViewById(R.id.placeholder_msg);
    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView = findViewById(R.id.news_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
        articleAdapter = new ArticleAdapter(this);
        recyclerView.setAdapter(articleAdapter);
    }

    private void observeNetworkState() {
        articleViewModel.getNetworkState().observe(this, networkState -> {
            Log.d("NETWORK STATE", "Changed: " + networkState.getMsg());
            articleAdapter.setNetworkState(networkState);
        });
    }

    private void observeNewsData() {
        articleViewModel.getPagedArticles().observe(this, articles -> {
            Log.d("LIVE DATA", "DB changed " + articles.size());
            if (articles.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
            }
            articleAdapter.submitList(articles);
        });
    }
}