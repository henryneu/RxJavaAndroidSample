package com.bkjk.rxjavasample.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bkjk.rxandroidsearch.R;
import com.bkjk.rxjavasample.model.BeautifulMapBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouzhenhua on 2018/4/27.
 */

public class ElementImageAdapter extends RecyclerView.Adapter<ElementImageAdapter.ImageViewHolder> {

    List<BeautifulMapBean> mBeautifulMapBeansList;

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_element_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        BeautifulMapBean beautifulMapBean = mBeautifulMapBeansList.get(position);
        Glide.with(holder.itemView.getContext()).load(beautifulMapBean.imageUrl).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return (mBeautifulMapBeansList == null || mBeautifulMapBeansList.size() == 0) ? 0 : mBeautifulMapBeansList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.element_item_image_view)
        ImageView mImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setImageList(List<BeautifulMapBean> beautifulMapBeansList) {
        mBeautifulMapBeansList = beautifulMapBeansList;
    }
}
