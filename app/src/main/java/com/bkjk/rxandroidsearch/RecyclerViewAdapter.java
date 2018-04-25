package com.bkjk.rxandroidsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouzhenhua on 2018/4/23.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<String> mQueryDataResult;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mResultTextView.setText(mQueryDataResult.get(position));
    }

    @Override
    public int getItemCount() {
        return (mQueryDataResult == null || mQueryDataResult.size() == 0) ? 0 : mQueryDataResult.size();
    }

    public void setQueryDataResult(List<String> result) {
        mQueryDataResult = result;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(android.R.id.text1)
        TextView mResultTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
