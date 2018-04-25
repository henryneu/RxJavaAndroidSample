package com.bkjk.rxandroidsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.click_search_button)
    Button mClickSearchButton;
    @BindView(R.id.search_edit_text)
    EditText mSearchEditText;
    @BindView(R.id.show_result_recycler_view)
    RecyclerView mShowResultRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.show_result_empty)
    View mResultEmptyView;

    protected QueryDataUtils mQueryDataUtils;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    // 查询所用到的数据集
    private List<String> mQueryDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        // 初始化数据
        initData();
        // 初始化查询工具
        mQueryDataUtils = new QueryDataUtils(mQueryDataList);
        // 初始化视图
        initView();
    }

    /**
     * 显示进度条
     */
    protected void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏进度条
     */
    protected void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * 展示查询到的数据
     * @param result
     */
    protected void showResult(List<String> result) {
        if (!result.isEmpty()) {
            mRecyclerViewAdapter.setQueryDataResult(result);
        } else {
            Toast.makeText(this, "query data not found", Toast.LENGTH_SHORT).show();
            mRecyclerViewAdapter.setQueryDataResult(Collections.<String>emptyList());
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 获取可查询的数据集
        mQueryDataList = Arrays.asList(getResources().getStringArray(R.array.querylist));
    }

    /**
     * 初始化视图
     */
    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mShowResultRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mShowResultRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
