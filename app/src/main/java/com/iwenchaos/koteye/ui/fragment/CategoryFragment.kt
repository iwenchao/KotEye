package com.iwenchaos.koteye.ui.fragment

import android.os.Bundle
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment

/**
 * Created by chaos
 * on 2018/5/5. 15:32
 * 文件描述：
 */
class CategoryFragment : BaseFragment() {


    companion object {

        fun getInstance(bundle: Bundle?): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_category

    override fun initUi() {
    }

    override fun lazyLoad() {
    }
}