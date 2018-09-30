package com.dhoomilbsheta.pagingnews.ui.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhoomilbsheta.pagingnews.R;
import com.dhoomilbsheta.pagingnews.data.NetworkState;

import androidx.recyclerview.widget.RecyclerView;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {
    private final ProgressBar progressBar;
    private final TextView errorMsg;
    private Button button;

    NetworkStateViewHolder(View view) {
        super(view);
        progressBar = view.findViewById(R.id.progress_bar);
        errorMsg = view.findViewById(R.id.error_msg);
        button = view.findViewById(R.id.retry_button);
    }

    void bind(NetworkState networkState) {
        if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

        if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText(networkState.getMsg());
        } else {
            errorMsg.setVisibility(View.GONE);
        }
    }
}
