package com.iwenchaos.koteye.mvp.presenter

import com.iwenchaos.koteye.base.BasePresenter
import com.iwenchaos.koteye.mvp.contract.FollowContract
import com.iwenchaos.koteye.mvp.model.FollowModel
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.net.exception.ExceptionHandle
import io.reactivex.functions.Consumer

/**
 * Created by chaos
 * on 2018/5/5. 16:10
 * 文件描述：
 */
class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {

    private var nextPageUrl: String? = null
    private val mModel by lazy {
        FollowModel()
    }

    override fun loadDta() {
        checkAttached()
        view?.showLoading()
        val dispose = mModel.loadDta().subscribe(Consumer<HomeInfo.Issue> { issue ->
            view?.apply {
                closeLoading()
                nextPageUrl = issue?.nextPageUrl
                view?.renderUi(issue)

            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable) {
                view?.apply {
                    closeLoading()
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            }
        })
        addSubscription(dispose)
    }

    override fun loadMore() {
    }
}