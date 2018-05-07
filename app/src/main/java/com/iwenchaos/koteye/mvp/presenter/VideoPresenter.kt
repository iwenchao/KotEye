package com.iwenchaos.koteye.mvp.presenter

import com.iwenchaos.koteye.base.BasePresenter
import com.iwenchaos.koteye.mvp.contract.VideoContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo

/**
 * Created by chaos
 * on 2018/5/6. 16:46
 * 文件描述：
 */
class VideoPresenter : BasePresenter<VideoContract.View>(), VideoContract.Presenter {


    override fun setVideoDetail(itemInfo: HomeInfo.Issue.Item) {

    }
}