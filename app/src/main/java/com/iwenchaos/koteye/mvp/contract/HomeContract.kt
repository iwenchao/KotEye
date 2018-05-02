package com.iwenchaos.koteye.mvp.contract

import com.iwenchaos.koteye.base.IModel
import com.iwenchaos.koteye.base.IPresenter
import com.iwenchaos.koteye.base.IView
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import io.reactivex.Observable

/**
 * Created by chaos
 * on 2018/5/1. 09:25
 * 文件描述：
 */
interface HomeContract {

    interface View : IView {

        fun setBanner(banner:HomeInfo)
        fun setContent(homeInfo:HomeInfo)

    }

    interface Presenter : IPresenter<View> {

        fun loadHomeDta()

        fun loadMoreDta()

    }

    interface Model : IModel {

        fun loadHomeDta(page: Int?): Observable<HomeInfo>
        fun loadHomeDta(pageUrl: String): Observable<HomeInfo>
        fun loadMoreDta(): Observable<HomeInfo>

    }

}