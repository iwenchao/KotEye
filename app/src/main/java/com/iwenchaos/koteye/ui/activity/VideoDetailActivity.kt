package com.iwenchaos.koteye.ui.activity

import android.annotation.TargetApi
import android.content.res.Configuration
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.widget.ImageView
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseActivity
import com.iwenchaos.koteye.base.EyeApplication
import com.iwenchaos.koteye.constant.Constants
import com.iwenchaos.koteye.glide.GlideApp
import com.iwenchaos.koteye.mvp.contract.VideoContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.mvp.presenter.VideoPresenter
import com.iwenchaos.koteye.toast
import com.iwenchaos.koteye.ui.adapter.VideoDetailAdapter
import com.iwenchaos.koteye.utils.CleanLeakUtils
import com.iwenchaos.koteye.utils.StatusBarUtil
import com.iwenchaos.koteye.utils.WatchHistoryUtils
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
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
    private var mVideoInfoData: HomeInfo.Issue.Item? = null
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
        SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE)
    }

    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    private var mVideoAdapter: VideoDetailAdapter? = null

    override fun layoutId() = R.layout.activity_video_detail


    override fun initUi() {
        mPresenter.attachView(this)
        mVideoData = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as HomeInfo.Issue.Item
        isTransition = intent.getBooleanExtra(Constants.TRANSITION, false)
        saveWatchVideoHistoryInfo(mVideoData)

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, vdVideoPlayer)
        //转场动画
        initTransition()
        //video config
        initVideoConfig()

        mVideoAdapter = VideoDetailAdapter(this, mVideoItemList)
        mVideoAdapter?.onItemClickListenter = { mPresenter.setVideoDetail(it) }
        vdRecyclerView.run {
            layoutManager = LinearLayoutManager(this@VideoDetailActivity)
            adapter = mVideoAdapter
        }

        //下拉刷新
        vdRefreshLayout.run {
            setEnableHeaderTranslationContent(true)
            setOnRefreshListener { mPresenter.setVideoDetail(mVideoData!!) }
            (refreshHeader as MaterialHeader).run {
                setShowBezierWave(true)
            }
            setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)
        }

    }


    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
//        GSYVideoPlayer.releaseAllVideos()
        mOrientationUtils?.releaseListener()
        mPresenter.detachView()
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (vdVideoPlayer.fullWindowPlayer != null) {
            vdVideoPlayer.fullWindowPlayer
        } else vdVideoPlayer
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
        vdVideoPlayer.setVideoAllCallBack(object : GSYSampleCallBack() {
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
        })

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

    }

    private fun setVideoInfo() {
        mPresenter.setVideoDetail(mVideoData)
    }


    override fun setVideo(url: String) {
        Logger.d("playUrl:$url")
        vdVideoPlayer.setUp(url, false, "")
        //开始自动播放
        vdVideoPlayer.startPlayLogic()
    }

    override fun setVideoInfo(itemInfo: HomeInfo.Issue.Item) {

        mVideoInfoData = itemInfo
        mVideoAdapter?.addVideoInfo(itemInfo)
        //开始请求相关视频数据
        mPresenter.getRelateList(itemInfo.data?.id!!)
    }

    override fun setBackground(url: String) {
        GlideApp.with(this)
                .load(url)
                .centerCrop()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .transition(DrawableTransitionOptions().crossFade())
                .into(mVideoBackground)
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeInfo.Issue.Item>) {
        mVideoAdapter?.addVideoRelateList(itemList)
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