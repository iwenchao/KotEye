package com.iwenchaos.koteye.mvp.contract

import com.iwenchaos.koteye.base.IModel
import com.iwenchaos.koteye.base.IPresenter
import com.iwenchaos.koteye.base.IView
import com.iwenchaos.koteye.mvp.model.bean.CategoryInfo
import io.reactivex.Observable

/**
 * Created by chaos
 * on 2018/5/6. 10:14
 * 文件描述：
 */
interface CategoryContract {

    interface View : IView {

        fun renderUi(infoList: ArrayList<CategoryInfo>)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {

        fun loadDta()

    }

    interface Model : IModel {

        fun loadDta(): Observable<ArrayList<CategoryInfo>>


    }
}