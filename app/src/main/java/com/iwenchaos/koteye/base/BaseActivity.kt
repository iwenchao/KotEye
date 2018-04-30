package com.iwenchaos.koteye.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.iwenchaos.koteye.widget.MultipleStatusView

/**
 * Created by chaos
 * on 2018/4/20. 10:56
 * 文件描述：
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    var layoutStatusView: MultipleStatusView? = null

    /**
     * 布局
     */
    abstract fun layoutId(): Int

    abstract fun initUi()

    abstract fun loadDta()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initUi()
        loadDta()
        layoutStatusView?.setOnClickListener(retryClickListener)


    }


    /**
     * 重试监听
     */
    open val retryClickListener = View.OnClickListener {
        loadDta()
    }

    override fun showLoading() {

    }

    override fun closeLoading() {

    }


    override fun onDestroy() {
        super.onDestroy()
//        EyeApplication.getRefWatcher(this)?.watch(this)
    }
}