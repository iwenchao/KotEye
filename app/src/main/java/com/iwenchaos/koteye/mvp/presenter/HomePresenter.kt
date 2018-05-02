package com.iwenchaos.koteye.mvp.presenter

import com.iwenchaos.koteye.base.BasePresenter
import com.iwenchaos.koteye.mvp.contract.HomeContract
import com.iwenchaos.koteye.mvp.model.HomeModel
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo

/**
 * Created by chaos
 * on 2018/5/1. 10:08
 * 文件描述：
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private var homeData: HomeInfo? = null
    private var page: Int = 1


    private val homeModel: HomeModel by lazy {
        HomeModel()
    }


    override fun loadHomeDta() {
        val disposable = homeModel.loadHomeDta(page)
                .flatMap({ homeInfo ->

                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = homeInfo.issueList[0].itemList

                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        bannerItemList.remove(item)
                    }

                    homeData = homeInfo //记录第一页是当做 banner 数据

//                    view?.setBanner(homeData!!)

                    //根据 nextPageUrl 请求下一页数据
                    homeModel.loadHomeDta(homeInfo.nextPageUrl)
                })
                .subscribe({ homeInfo ->
                    view?.apply {
                        closeLoading()

//                        nextPageUrl = homeBean.nextPageUrl
                        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                        val newBannerItemList = homeInfo.issueList[0].itemList

                        newBannerItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //移除 item
                            newBannerItemList.remove(item)
                        }
                        // 重新赋值 Banner 长度
                        homeData!!.issueList[0].count = homeData!!.issueList[0].itemList.size

                        //赋值过滤后的数据 + banner 数据
                        homeData?.issueList!![0].itemList.addAll(newBannerItemList)

                        view?.setContent(homeData!!)
//                        setHomeData(homeData!!)

                    }

                }, { t ->
                    view?.apply {
                        closeLoading()
//                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposable)

    }

    override fun loadMoreDta() {

    }


}