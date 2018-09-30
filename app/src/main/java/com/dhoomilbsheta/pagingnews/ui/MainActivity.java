package com.dhoomilbsheta.pagingnews.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dhoomilbsheta.pagingnews.ArticleViewModel;
import com.dhoomilbsheta.pagingnews.R;
import com.dhoomilbsheta.pagingnews.ui.adapter.ArticleAdapter;

public class MainActivity extends AppCompatActivity {
    private ArticleViewModel articleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureViewModel();
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.news_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
        final ArticleAdapter articleAdapter = new ArticleAdapter(this);
        recyclerView.setAdapter(articleAdapter);

        articleViewModel.getPagedArticles().observe(this, articles -> {
            Log.d("LIVE DATA", "Change observed! " + articles.size());
            articleAdapter.submitList(articles);
        });
    }

    private void configureViewModel() {
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        articleViewModel.getNetworkState().observe(this, networkState -> {
            Log.d("NETWORK STATE", "Changed: " + networkState.getMsg());
            Toast.makeText(this, networkState.getMsg(), Toast.LENGTH_SHORT).show();
        });
    }
}