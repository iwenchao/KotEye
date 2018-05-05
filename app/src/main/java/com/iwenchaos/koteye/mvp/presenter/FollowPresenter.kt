package com.iwenchaos.koteye.mvp.presenter

import com.iwenchaos.koteye.base.BasePresenter
import com.iwenchaos.koteye.mvp.contract.FollowContract
import com.iwenchaos.koteye.mvp.model.FollowModel

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
        view?.showLoading()
        mModel.loadDta()
    }

    override fun loadMore() {
    }
}