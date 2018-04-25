package com.bkjk.rxandroidsearch;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private Disposable mDisposable;

    @Override
    protected void onStart() {
        super.onStart();

        Observable<String> clickButtonObservable = createButtonClickObservable();
        Observable<String> textChangedObservable = createTextChangedObservable();
        // merge 可以将两个或更多的 observable 联合起来，合成一个单一的 observable
        Observable<String> searchTextObservable = Observable.merge(clickButtonObservable, textChangedObservable);

//        clickButtonObservable
//        textChangedObservable
        mDisposable = searchTextObservable
                // observeOn 可以在链上调用多次，它主要是用来指定下一个操作在哪一个线程上执行；
                // 下一步操作在 主 线程上面
                .observeOn(AndroidSchedulers.mainThread())
                // 使用 doOnNext 显示进度条
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        showProgressBar();
                    }
                })
                // 下一步操作在 I/O 线程上
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String inputQuery) throws Exception {
                        return mQueryDataUtils.search(inputQuery);
                    }
                })
                // 下一步操作在 主 线程上面
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> queryResult) throws Exception {
                        hideProgressBar();
                        showResult(queryResult);
                    }
                });
    }

    private Observable<String> createButtonClickObservable() {
        // 创建一个Observable，并创建了一个ObservableOnSubscribe
        return Observable.create(new ObservableOnSubscribe<String>() {

            // 内部类中覆写了 subscribe() 方法
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                mClickSearchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emitter.onNext(mSearchEditText.getText().toString());
                    }
                });

                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        mClickSearchButton.setOnClickListener(null);
                    }
                });
            }
        });
    }

    private Observable<String> createTextChangedObservable() {
        // 创建一个 Observable，并创建了一个 ObservableOnSubscribe
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                // 创建一个 TextWatcher，这是用来监听 EditText 中值的变化
                final TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence inputSearch, int start, int before, int count) {
                        emitter.onNext(inputSearch.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };

                // 为 EditText 添加监听
                mSearchEditText.addTextChangedListener(textWatcher);

                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        // EditText 移除监听
                        mSearchEditText.removeTextChangedListener(textWatcher);
                    }
                });
            }
        }).filter(new Predicate<String>() {  // 内容长度拦截过滤
            @Override
            public boolean test(String inputSearch) throws Exception {
                // 只有输入的字符串长度大于等于3时返回true，开始搜索
                return inputSearch.length() >= 3;
            }
        }).debounce(1000, TimeUnit.MILLISECONDS);  // debounce 操作符，一种拦截策略，防止抖动
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 如果没有解除订阅，则解除
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
