package com.iwenchaos.koteye.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.ui.adapter.DiscoveryPagerAdapter
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 * Created by chaos
 * on 2018/5/3. 11:30
 * 文件描述：
 */
class DiscoveryFragment : BaseFragment() {

    //与ArrayList（）的区别？
    private var tabTxtList = listOf("关注", "分类")
    private var tabPageList = ArrayList<Fragment>()
    private lateinit var toolBarTitle: String
    private var pagerAdapter: DiscoveryPagerAdapter? = null


    companion object {
        fun getInstance(title: String, bundle: Bundle?): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            fragment.arguments = bundle
            fragment.toolBarTitle = title
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_discovery

    override fun initUi() {
        tvHeader.text = toolBarTitle
        tabPageList.add(FollowFragment.getInstance(null))
        tabPageList.add(CategoryFragment.getInstance(null))
        pagerAdapter = DiscoveryPagerAdapter(childFragmentManager, tabPageList, tabTxtList)
        disViewPager.run {
            adapter = pagerAdapter
        }
        disTabLayout.setupWithViewPager(disViewPager)
//        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)

    }

    override fun lazyLoad() {
    }
}