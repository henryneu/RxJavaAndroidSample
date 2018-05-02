package com.bkjk.rxjavasample.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bkjk.rxandroidsearch.R;
import com.bkjk.rxjavasample.Adapter.ElementImageAdapter;
import com.bkjk.rxjavasample.model.BeautifulMapBean;
import com.bkjk.rxjavasample.model.BeautifulResult;
import com.bkjk.rxjavasample.networks.NetWorks;
import com.bkjk.rxjavasample.utils.BeautifulResultToMapBeanMapper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouzhenhua on 2018/4/27.
 */

public class ElementFragment extends BaseFragment {

    private static final String TAG = ElementFragment.class.getSimpleName();

    @BindView(R.id.element_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.element_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.floating_action_previous_button)
    FloatingActionButton mPreviousButton;
    @BindView(R.id.floating_action_next_button)
    FloatingActionButton mNextButton;

    private int pages = 0;

    ElementImageAdapter mElementImageAdapter = new ElementImageAdapter();

    // @OnClick({R.id.floating_action_previer_button, R.id.floating_action_next_button})
    @OnClick(R.id.floating_action_previous_button)
    void previousPages() {
        loadingPage(--pages);
        if (pages == 1) {
            mPreviousButton.setEnabled(false);
        }
    }

    @OnClick(R.id.floating_action_next_button)
    void nextPages() {
        loadingPage(++pages);
        if (pages == 2) {
            mPreviousButton.setEnabled(true);
        }
    }

    /**
     * 根据传入的字符串查询对应的图片列表
     *
     * @param index
     */
    private void loadingPage(int index) {
        mSwipeRefreshLayout.setRefreshing(true);
        unSubscribe();
        mDisposable = NetWorks.getBeautifulApi()
                .search(20, index)
                // map 转换事件对象
                .map(BeautifulResultToMapBeanMapper.getInstance())
                // I/O 线程进行耗时的查询操作
                .subscribeOn(Schedulers.io())
                // 主线程展示查询到的结果
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BeautifulMapBean>>() {
                    @Override
                    public void accept(@Nullable List<BeautifulMapBean> beautifulMapBeans) throws Exception {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mElementImageAdapter.setImageList(beautifulMapBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "数据加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_element, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mElementImageAdapter);
        // 设置下拉刷新进度条的颜色
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSwipeRefreshLayout.setRefreshing(false);
        return view;
    }
}
