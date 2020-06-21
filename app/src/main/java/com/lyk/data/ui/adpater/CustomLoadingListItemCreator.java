package com.lyk.data.ui.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lyk.data.R;
import com.paginate.recycler.LoadingListItemCreator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomLoadingListItemCreator implements LoadingListItemCreator {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_load_more,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_view)
        ProgressBar progressView;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
