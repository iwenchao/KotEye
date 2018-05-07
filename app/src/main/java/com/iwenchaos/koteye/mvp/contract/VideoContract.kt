package com.iwenchaos.koteye.mvp.contract

import com.iwenchaos.koteye.base.IModel
import com.iwenchaos.koteye.base.IPresenter
import com.iwenchaos.koteye.base.IView
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo

/**
 * Created by chaos
 * on 2018/5/6. 16:38
 * 文件描述：
 */
interface VideoContract {


    interface View : IView {
        /**
         * 设置视频播放源
         */
        fun setVideo(url: String)

        /**
         * 设置视频信息
         */
        fun setVideoInfo(itemInfo: HomeInfo.Issue.Item)

        /**
         * 设置背景
         */
        fun setBackground(url: String)

        /**
         * 设置最新相关视频
         */
        fun setRecentRelatedVideo(itemList: ArrayList<HomeInfo.Issue.Item>)

        /**
         * 设置错误信息
         */
        fun setErrorMsg(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        fun setVideoDetail(itemInfo: HomeInfo.Issue.Item)

    }

    interface Model : IModel {


    }

}