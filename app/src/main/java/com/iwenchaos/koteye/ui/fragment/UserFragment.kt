package com.iwenchaos.koteye.ui.fragment

import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment

/**
 * Created by chaos
 * on 2018/5/3. 14:44
 * 文件描述：
 */
class UserFragment : BaseFragment() {


    private  var mTitle: String? = null

    companion object {
        //伴随对象 获取fragment实例
        fun getInstance(title: String): UserFragment {
            val f = UserFragment()
            f.mTitle = title
            return f
        }
    }


    override fun getLayoutId() = R.layout.fragment_user

    override fun initUi() {
    }

    override fun lazyLoad() {
    }
}