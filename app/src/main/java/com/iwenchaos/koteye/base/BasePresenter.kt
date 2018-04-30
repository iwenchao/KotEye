package com.iwenchaos.koteye.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by chaos
 * on 2018/4/30. 12:53
 * 文件描述：
 */
abstract class BasePresenter<V : IView> : IPresenter<V> {
    //ui
    var view: V? = null
        private set
    val isAttach: Boolean
        get() = view != null

    private var compositeDisposable = CompositeDisposable()


    override fun attachView(view: V) {
        this.view = view
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    override fun detachView() {
        this.view = null
        //与view解除bind关系时，同时也把事件订阅解除
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun checkAttached() {
        if (!isAttach) throw ViewNotAttachedException()
    }


    class ViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before\" " +
            "+ \" requesting data to the IPresenter\"")
}