package com.iwenchaos.koteye.mvp.presenter

import android.app.Activity
import com.iwenchaos.koteye.base.BasePresenter
import com.iwenchaos.koteye.base.EyeApplication
import com.iwenchaos.koteye.dataformat
import com.iwenchaos.koteye.mvp.contract.VideoContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.net.RetrofitManager
import com.iwenchaos.koteye.toast
import com.iwenchaos.koteye.utils.DisplayManager
import com.iwenchaos.koteye.utils.NetworkUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by chaos
 * on 2018/5/6. 16:46
 * 文件描述：
 */
class VideoPresenter : BasePresenter<VideoContract.View>(), VideoContract.Presenter {


    override fun setVideoDetail(itemInfo: HomeInfo.Issue.Item?) {

        val playInfo = itemInfo?.data?.playInfo
        val netType = NetworkUtil.isWifi(EyeApplication.context)
        checkAttached()
        if (playInfo!!.size > 1) {
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        view?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                //否则就选标清的视频
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        view?.setVideo(playUrl)

                        (view as Activity).toast("本次消耗${(view as Activity)
                                .dataformat(i.urlList[0].size)}流量")
                        break
                    }
                }
            }
        } else {
            view?.setVideo(itemInfo.data.playUrl)
        }

        //设置背景
        val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { view?.setBackground(it) }

        view?.setVideoInfo(itemInfo)

    }


    override fun getRelateList(videoId: Long) {
        checkAttached()
        view?.showLoading()
        val disposable = RetrofitManager.service.getRelateVideoList(videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<HomeInfo.Issue> {
                    view?.closeLoading()
                    view?.setRecentRelatedVideo(it.itemList)

                }, object : Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        view?.closeLoading()
                    }
                })
        addSubscription(disposable)
    }
}