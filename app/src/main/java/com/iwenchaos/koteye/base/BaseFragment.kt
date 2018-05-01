package com.iwenchaos.koteye.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iwenchaos.koteye.widget.MultipleStatusView

/**
 * Created by chaos
 * on 2018/4/30. 16:08
 * 文件描述：采用lazy load的方式
 */
abstract class BaseFragment : Fragment() {

    private var isUiPrepared = false
    private var isDataLoaded = false
    private var container: View? = null
    private var layoutStatusView: MultipleStatusView? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initUi()

    abstract fun lazyLoad()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.container ?: let {
            this.container = inflater.inflate(getLayoutId(), container, false)
        }
        return this.container
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDtaIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        isUiPrepared = true
        lazyLoadDtaIfPrepared()
        layoutStatusView?.setOnClickListener { lazyLoad() }
    }

    private fun lazyLoadDtaIfPrepared() {
        if (userVisibleHint && isUiPrepared && !isDataLoaded) {
            lazyLoad()
            isDataLoaded = true
        }
    }

    fun showLoading() {

    }

    fun closeLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()

    }

}