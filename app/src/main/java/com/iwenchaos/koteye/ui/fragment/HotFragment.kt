package com.iwenchaos.koteye.ui.fragment

import android.os.Bundle
import com.iwenchaos.koteye.base.BaseFragment

/**
 * Created by chaos
 * on 2018/5/6. 10:59
 * 文件描述：
 */
class HotFragment : BaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String, bundle: Bundle?): HotFragment {
            val fragment = HotFragment()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }


    override fun getLayoutId() = 0

    override fun initUi() {


    }

    override fun lazyLoad() {
    }
}