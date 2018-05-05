package com.iwenchaos.koteye.mvp.contract

import com.iwenchaos.koteye.base.IModel
import com.iwenchaos.koteye.base.IPresenter
import com.iwenchaos.koteye.base.IView
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import io.reactivex.Observable

/**
 * Created by chaos
 * on 2018/5/5. 16:06
 * 文件描述：
 */
interface FollowContract {

    interface View : IView {

        fun renderUi(issue: HomeInfo.Issue)

    }

    interface Presenter : IPresenter<View> {

        fun loadDta()
        fun loadMore()

    }

    interface Model : IModel {

        fun loadDta(): Observable<HomeInfo.Issue>

        fun loadMore(pageUrl: String?): Observable<HomeInfo.Issue>

    }

}