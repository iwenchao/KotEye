package com.iwenchaos.koteye.ui.activity

import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.iwenchaos.koteye.BuildConfig
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseActivity
import com.iwenchaos.koteye.mvp.model.bean.TabEntity
import com.iwenchaos.koteye.toast
import com.iwenchaos.koteye.ui.fragment.HomeFragment
import com.iwenchaos.koteye.ui.fragment.UserFragment
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

    private var homeFragment: HomeFragment? = null
    private var discoverFragment: HomeFragment? = null
    private var hotFragment: HomeFragment? = null
    private var userFragment: UserFragment? = null

    /**
     * 布局
     */
    override fun layoutId() = R.layout.activity_main

    override fun initUi() {
        initTab(mTabIndex)//default
        switchFragment(mTabIndex)

    }

    override fun loadDta() {}

    private fun initTab(tabIndex: Int) {

        (0 until mTabTitles.size).mapTo(mTabs) {
            TabEntity(mTabTitles[it], mSelectedIconIds[it], mUnSelectIconIds[it])
        }
        tab_layout.setTabData(mTabs)
        tab_layout.currentTab = tabIndex
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                if (BuildConfig.DEBUG) toast("当前：" + position)
                //switch fragment
                switchFragment(position)

            }

            override fun onTabReselect(position: Int) {
                //下拉刷新，处理双击事件

            }

        })

    }

    private fun switchFragment(tab: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (tab) {
            0 -> homeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTabTitles[tab]).let {
                homeFragment = it
                transaction.add(R.id.fl_container, it, "home")
            }
            1 -> discoverFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTabTitles[tab]).let {
                discoverFragment = it
                transaction.add(R.id.fl_container, it, "discover")
            }
            2 -> hotFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTabTitles[tab]).let {
                hotFragment = it
                transaction.add(R.id.fl_container, it, "hot")
            }
            3 -> userFragment?.let {
                transaction.show(it)
            } ?: UserFragment.getInstance(mTabTitles[tab]).let {
                userFragment = it
                transaction.add(R.id.fl_container, it, "user")
            }
        }
        mTabIndex = tab
        tab_layout.currentTab = mTabIndex
        transaction.commitAllowingStateLoss()
    }

    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        discoverFragment?.let { transaction.hide(it) }
        hotFragment?.let { transaction.hide(it) }
        userFragment?.let { transaction.hide(it) }
    }

    private var mExitTime: Long = 0L

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                toast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}