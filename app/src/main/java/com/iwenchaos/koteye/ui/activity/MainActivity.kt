package com.iwenchaos.koteye.ui.activity

import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.iwenchaos.koteye.BuildConfig
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseActivity
import com.iwenchaos.koteye.mvp.model.bean.TabEntity
import com.iwenchaos.koteye.toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by chaos
 * on 2018/4/30. 13:46
 * 文件描述：
 */
class MainActivity : BaseActivity() {

    //tab相关
    private val mTabTitles = arrayOf("每日精选", "发现", "热门", "我的")
    private val mUnSelectIconIds = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal,
            R.mipmap.ic_hot_normal, R.mipmap.ic_mine_normal)
    private val mSelectedIconIds = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected,
            R.mipmap.ic_hot_selected, R.mipmap.ic_mine_selected)
    private val mTabs = ArrayList<CustomTabEntity>()
    private var mTabIndex = 0


    /**
     * 布局
     */
    override fun layoutId() = R.layout.activity_main

    override fun initUi() {
        initTab()

    }

    override fun loadDta() {
    }

    private fun initTab() {

        (0 until mTabTitles.size).mapTo(mTabs) {
            TabEntity(mTabTitles[it], mUnSelectIconIds[it], mSelectedIconIds[it])
        }
        tab_layout.setTabData(mTabs)
        tab_layout.currentTab = mTabIndex
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                if (BuildConfig.DEBUG) toast("当前：" + position)
                //switch fragment


            }

            override fun onTabReselect(position: Int) {
            }

        })
    }
}