package com.iwenchaos.koteye.mvp.presenter

import com.iwenchaos.koteye.base.BasePresenter
import com.iwenchaos.koteye.mvp.contract.CategoryContract
import com.iwenchaos.koteye.mvp.model.CategoryModel
import com.iwenchaos.koteye.mvp.model.bean.CategoryInfo
import com.iwenchaos.koteye.net.exception.ExceptionHandle
import io.reactivex.functions.Consumer

/**
 * Created by chaos
 * on 2018/5/6. 10:35
 * 文件描述：
 */
class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val mModel by lazy {
        CategoryModel()
    }

    override fun loadDta() {
        checkAttached()
        view?.showLoading()
        val dispose = mModel.loadDta().subscribe(object : Consumer<ArrayList<CategoryInfo>> {
            override fun accept(dataList: ArrayList<CategoryInfo>?) {
                view?.closeLoading()
                dataList?.let {
                    view?.renderUi(dataList)
                } ?: let {
                    view?.showError(ExceptionHandle.handleException(IllegalArgumentException("返回数据为null")), ExceptionHandle.errorCode)
                }

            }

        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable) {
                view?.closeLoading()
                view?.showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)

            }

        })
        addSubscription(dispose)
    }
}