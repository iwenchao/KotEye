package com.iwenchaos.koteye.mvp.contract

import com.iwenchaos.koteye.base.IModel
import com.iwenchaos.koteye.base.IPresenter
import com.iwenchaos.koteye.base.IView

/**
 * Created by chaos
 * on 2018/5/1. 09:25
 * 文件描述：
 */
interface HomeContract {

    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }

    interface Model : IModel {

    }

}