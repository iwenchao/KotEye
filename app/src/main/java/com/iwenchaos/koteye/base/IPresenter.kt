package com.iwenchaos.koteye.base

/**
 * Created by chaos
 * on 2018/4/30. 12:50
 * 文件描述：
 */
interface IPresenter<in V : IView> {

    fun attachView(view: V)
    fun detachView()
}