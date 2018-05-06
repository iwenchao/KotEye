package com.iwenchaos.koteye.mvp.contract

import com.iwenchaos.koteye.base.IModel
import com.iwenchaos.koteye.base.IPresenter
import com.iwenchaos.koteye.base.IView
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import io.reactivex.Observable

/**
 * Created by chaos
 * on 2018/5/6. 13:57
 * 文件描述：
 */
interface HotTabContract {


    interface View : IView {

        fun renderUi(data: HomeInfo.Issue)
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {

        fun loadDta(pageUrl: String)

    }

    interface Model : IModel {

        fun loadDta(pageUrl: String): Observable<HomeInfo.Issue>

    }

}