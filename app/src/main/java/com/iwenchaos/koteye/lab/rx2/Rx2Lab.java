package com.iwenchaos.koteye.lab.rx2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chaos
 * on 2018/5/8. 10:32
 * 文件描述：
 */

public class Rx2Lab {

    public static void main(String[] args) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                for (int i = 0; i < 10; i++) {
                    if (i % 2 == 0) {
                        e.onNext(String.valueOf(i));
                    } else {
                        e.isDisposed();
                    }
                }
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });


        /////////////////
        CompositeDisposable comDisposable = new CompositeDisposable();
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("hello");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Disposable disposable = observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
            }
        });
        comDisposable.add(disposable);

    }

}
