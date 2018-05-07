package com.iwenchaos.koteye.ui.activity

import android.annotation.TargetApi
import android.content.res.Configuration
import android.os.Build
import android.support.v4.view.ViewCompat
import android.transition.Transition
import android.widget.ImageView
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseActivity
import com.iwenchaos.koteye.base.EyeApplication
import com.iwenchaos.koteye.constant.Constants
import com.iwenchaos.koteye.glide.GlideApp
import com.iwenchaos.koteye.mvp.contract.VideoContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.mvp.presenter.VideoPresenter
import com.iwenchaos.koteye.toast
import com.iwenchaos.koteye.utils.WatchHistoryUtils
import com.orhanobut.logger.Logger
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by chaos
 * on 2018/5/6. 10:06
 * 文件描述：
 */
class VideoDetailActivity : BaseActivity(), VideoContract.View {


    private var mVideoData: HomeInfo.Issue.Item? = null
    private var mOrientationUtils: OrientationUtils? = null
    private var mVideoItemList = ArrayList<HomeInfo.Issue.Item>()
    private val mPresenter by lazy {
        VideoPresenter()
    }
    //转场动画
    private var isTransition: Boolean = false
    private var mTransition: Transition? = null
    //    private var mVideoHelper: GSYVideoHelper? = null
    private var mVideoHelperBuilder: GSYVideoHelper.GSYVideoHelperBuilder? = null

    private val mDataFormat by lazy {
        SimpleDateFormat("yyyyMMddHHmmss")
    }

    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    override fun layoutId() = R.layout.activity_video_detail

    override fun initUi() {
        mPresenter.attachView(this)
        //转场动画
        initTransition()
        //video config
        initVideoConfig()

    }

    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(vdVideoPlayer, Constants.IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            setVideoInfo()
        }
    }

    private fun initVideoConfig() {
        //旋转util
        mOrientationUtils = OrientationUtils(this, vdVideoPlayer)
//        mVideoHelper = GSYVideoHelper(this)
        mVideoHelperBuilder = GSYVideoHelper.GSYVideoHelperBuilder()
        //是否自动旋转
        vdVideoPlayer.isRotateViewAuto = false
        vdVideoPlayer.setIsTouchWiget(true)

        mVideoHelperBuilder?.run {
            videoAllCallBack = object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    mOrientationUtils?.isEnable = true
                    isPlay = true
                }

                override fun onAutoComplete(url: String, vararg objects: Any) {
                    super.onAutoComplete(url, *objects)
                    Logger.d("***** onAutoPlayComplete **** ")
                }

                override fun onPlayError(url: String, vararg objects: Any) {
                    super.onPlayError(url, *objects)
                    toast("播放失败")
                }

                override fun onEnterFullscreen(url: String, vararg objects: Any) {
                    super.onEnterFullscreen(url, *objects)
                    Logger.d("***** onEnterFullscreen **** ")
                }

                override fun onQuitFullscreen(url: String, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    Logger.d("***** onQuitFullscreen **** ")
                    //列表返回的样式判断
                    mOrientationUtils?.backToProtVideo()
                }
            }
            build(vdVideoPlayer)
        }

        //首帧画面
        val framePage = ImageView(this)
        framePage.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideApp.with(this)
                .load(mVideoData?.data?.cover?.feed)
                .circleCrop()
                .into(framePage)
        vdVideoPlayer.run {
            thumbImageView = framePage
            //设置返回按键功能
            backButton.setOnClickListener({ onBackPressed() })
            //设置全屏按键功能
            fullscreenButton.setOnClickListener({
                //直接横屏
                mOrientationUtils?.resolveByClick()
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                vdVideoPlayer.startWindowFullscreen(this@VideoDetailActivity, true, true)
                setLockClickListener { _, lock ->
                    //配合下方的onConfigurationChanged
                    mOrientationUtils?.isEnable = !lock
                }
            })
        }


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        mTransition = window.sharedElementEnterTransition
        mTransition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(p0: Transition?) {
                setVideoInfo()
                mTransition?.removeListener(this)
            }

            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            vdVideoPlayer.onConfigurationChanged(this, newConfig, mOrientationUtils)
        }
    }

    override fun loadDta() {
        mVideoData = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as HomeInfo.Issue.Item
        isTransition = intent.getBooleanExtra(Constants.TRANSITION, false)

        saveWatchVideoHistoryInfo(mVideoData)
    }

    private fun setVideoInfo() {

    }


    override fun setVideo(url: String) {
    }

    override fun setVideoInfo(itemInfo: HomeInfo.Issue.Item) {
    }

    override fun setBackground(url: String) {
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeInfo.Issue.Item>) {
    }

    override fun setErrorMsg(errorMsg: String) {
    }


    private fun saveWatchVideoHistoryInfo(watchItem: HomeInfo.Issue.Item?) {
        val historyMap = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME, EyeApplication.context) as Map<*, *>
        for ((key, _) in historyMap) {
            if (watchItem == WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME, EyeApplication.context, key as String)) {
                WatchHistoryUtils.remove(Constants.FILE_WATCH_HISTORY_NAME, EyeApplication.context, key)
            }
        }
        WatchHistoryUtils.putObject(Constants.FILE_WATCH_HISTORY_NAME, EyeApplication.context, watchItem, mDataFormat.format(Date()) + "")
    }


    override fun onBackPressed() {
        super.onBackPressed()
        //需要释放视频播放所持有的资源
        mOrientationUtils?.backToProtVideo()

        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
        }
    }
}