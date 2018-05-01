package com.iwenchaos.koteye.ui.fragment

import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.mvp.contract.HomeContract

/**
 * Created by chaos
 * on 2018/5/1. 09:27
 * 文件描述：
 */
class HomeFragment : BaseFragment(), HomeContract.View {


    override fun getLayoutId() = R.layout.fragment_home


    override fun initUi() {
    }

    override fun lazyLoad() {
    }
}