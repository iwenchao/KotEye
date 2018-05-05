package com.iwenchaos.koteye.ui.fragment

import android.os.Bundle
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment

/**
 * Created by chaos
 * on 2018/5/5. 15:32
 * 文件描述：
 */
class FollowFragment : BaseFragment() {


    companion object {

        fun getInstance(bundle: Bundle?): FollowFragment {
            val fragment = FollowFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_follow

    override fun initUi() {
    }

    override fun lazyLoad() {
    }
}