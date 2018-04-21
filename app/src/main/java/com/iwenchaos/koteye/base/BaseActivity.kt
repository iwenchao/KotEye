package com.iwenchaos.koteye.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iwenchaos.koteye.widget.MultipleStatusView

/**
 * Created by chaos
 * on 2018/4/20. 10:56
 * 文件描述：
 */
abstract class BaseActivity : AppCompatActivity(){

    protected var layoutStatusView: MultipleStatusView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}