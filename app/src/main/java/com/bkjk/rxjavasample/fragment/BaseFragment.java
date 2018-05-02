package com.bkjk.rxjavasample.fragment;

import android.app.Fragment;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhouzhenhua on 2018/4/27.
 */

public abstract class BaseFragment extends Fragment {

    protected Disposable mDisposable;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unSubscribe();
    }

    protected void unSubscribe() {
        if (mDisposable != null || !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
