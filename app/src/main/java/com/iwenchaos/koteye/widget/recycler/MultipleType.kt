package com.iwenchaos.koteye.widget.recycler

/**
 * Created by chaos
 * on 2018/5/1. 11:06
 * 文件描述：recycler multi view
 */

interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}
