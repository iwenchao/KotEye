package com.iwenchaos.koteye.mvp.contract

import com.iwenchaos.koteye.base.IModel
import com.iwenchaos.koteye.base.IPresenter
import com.iwenchaos.koteye.base.IView
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import io.reactivex.Observable

/**
 * Created by chaos
 * on 2018/5/3. 14:43
 * 文件描述：
 */
interface UserContract {


    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

        fun loadDta()

    }

    interface Model : IModel {

        fun loadDta(page: Int?): Observable<HomeInfo>

    }
}