package com.iwenchaos.koteye.mvp.presenter

import com.iwenchaos.koteye.base.BasePresenter
import com.iwenchaos.koteye.mvp.contract.HotTabContract
import com.iwenchaos.koteye.mvp.model.HotTabModel
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.net.exception.ExceptionHandle
import io.reactivex.functions.Consumer

/**
 * Created by chaos
 * on 2018/5/6. 14:00
 * 文件描述：
 */
class HotTabPresenter : BasePresenter<HotTabContract.View>(), HotTabContract.Presenter {


    private val mModel by lazy {
        HotTabModel()
    }

    override fun loadDta(pageUrl: String) {
        checkAttached()
        view?.showLoading()
        val disposible = mModel.loadDta(pageUrl).subscribe(object : Consumer<HomeInfo.Issue> {
            override fun accept(data: HomeInfo.Issue?) {
                view?.closeLoading()
                data?.let {
                    view?.renderUi(data)
                } ?: let {
                    view?.showError(ExceptionHandle.handleException(IllegalArgumentException("返回数据为null")), ExceptionHandle.errorCode)

                }
            }

        }, Consumer<Throwable> { t -> view?.showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode) })

        addSubscription(disposible)
    }
}