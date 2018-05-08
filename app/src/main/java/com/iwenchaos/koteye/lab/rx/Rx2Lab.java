package com.iwenchaos.koteye.lab.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
        System.out.println("//////////////////////////////////");
        syncProcessAssociate();
        System.out.println("//////////////////////////////////");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println(Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    if (i % 2 == 0) {
                        e.onNext(String.valueOf(i));
                    } else {
                        e.isDisposed();
                    }
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(Thread.currentThread().getName());
                System.out.println(s);
            }
        });
        System.out.println("//////////////////////////////////");

        /////////////////
        CompositeDisposable comDisposable = new CompositeDisposable();
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                System.out.println(Thread.currentThread().getName());
                emitter.onNext("hello");
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .onTerminateDetach();
        Disposable disposable = observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println(Thread.currentThread().getName());

            }
        });
        comDisposable.add(disposable);

    }


    /**
     * 场景：类似异步之间的进度显示
     *
     * @方式0 Thread+handler+looper
     * @方式1 AsyncTask
     * @方式2 Rx
     */
    private static void syncProcessAssociate() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < 100; i++) {
                if (i % 20 == 0) {
                    try {
                        Thread.sleep(700);//充当耗时操作
                    } catch (InterruptedException e) {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    }
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(integer -> System.out.println(integer + ""));
    }

}
