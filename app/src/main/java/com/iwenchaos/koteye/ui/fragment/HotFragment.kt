package com.iwenchaos.koteye.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.ui.adapter.LocalPagerAdapter
import com.iwenchaos.koteye.utils.StatusBarUtil
import com.iwenchaos.koteye.utils.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by chaos
 * on 2018/5/6. 10:59
 * 文件描述：
 */
class HotFragment : BaseFragment() {

    private var mTitle: String? = null
    private var tabTxtList = listOf("周排行", "月排行", "总排行")
    private var tabPageList = ArrayList<Fragment>()

    companion object {
        fun getInstance(title: String, bundle: Bundle?): HotFragment {
            val fragment = HotFragment()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_hot

    override fun initUi() {

        StatusBarUtil.darkMode(activity!!)
        StatusBarUtil.setPaddingSmart(activity!!, hotToolbar)

        tabPageList.apply {
            add(CategoryFragment.getInstance(null))
            add(CategoryFragment.getInstance(null))
            add(CategoryFragment.getInstance(null))
        }
        hotViewPager.run {
            adapter = LocalPagerAdapter(childFragmentManager,tabPageList,tabTxtList)
        }
        hotTabLayout.setupWithViewPager(hotViewPager)
        TabLayoutHelper.setUpIndicatorWidth(hotTabLayout)



    }

    override fun lazyLoad() {
    }
}