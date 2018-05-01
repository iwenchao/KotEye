package com.iwenchaos.koteye.mvp.presenter

import com.iwenchaos.koteye.base.BasePresenter
import com.iwenchaos.koteye.mvp.contract.HomeContract
import com.iwenchaos.koteye.mvp.model.HomeModel

/**
 * Created by chaos
 * on 2018/5/1. 10:08
 * 文件描述：
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {



    private val homeModel: HomeModel by lazy {
        HomeModel()
    }


    override fun loadHomeDta(page:Int?) {
        homeModel.loadHomeDta(page)
    }

    override fun loadMoreDta() {

    }


}