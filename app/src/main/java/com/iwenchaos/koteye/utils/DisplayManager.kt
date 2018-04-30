package com.iwenchaos.koteye.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by chaos
 * on 2018/4/30. 12:38
 * 文件描述：
 */
object DisplayManager {

    init {

    }

    //设备的屏幕信息
    private var displayMetrics: DisplayMetrics? = null
    private var sWidth: Int? = null
    private var sHeight: Int? = null
    private var sDpi: Int? = null
    //UI
    private val STANDARD_WIDTH = 1080
    private val STANDARD_HEIGHT = 1920

    fun init(context: Context) {
        displayMetrics = context.resources.displayMetrics
        sWidth = displayMetrics?.widthPixels
        sHeight = displayMetrics?.heightPixels
        sDpi = displayMetrics?.densityDpi
    }

    /**
     * 输入UI图的尺寸，输出实际的px
     *
     * @param px ui图中的大小
     * @return
     */
    fun getRealWidth(px: Int): Int? {
        //ui图的宽度
        return getRealWidth(px, STANDARD_WIDTH.toFloat())
    }

    /**
     * 输入UI图的尺寸，输出实际的px,第二个参数是父布局
     *
     * @param px          ui图中的大小
     * @param parentWidth 父view在ui图中的高度
     * @return
     */
    fun getRealWidth(px: Int, parentWidth: Float): Int? {
        return (px / parentWidth * sWidth!!).toInt()
    }

    /**
     * 输入UI图的尺寸，输出实际的px
     *
     * @param px ui图中的大小
     * @return
     */
    fun getRealHeight(px: Int): Int? {
        //ui图的宽度
        return getRealHeight(px, STANDARD_HEIGHT.toFloat())
    }

    /**
     * 输入UI图的尺寸，输出实际的px,第二个参数是父布局
     *
     * @param px           ui图中的大小
     * @param parentHeight 父view在ui图中的高度
     * @return
     */
    fun getRealHeight(px: Int, parentHeight: Float): Int? {
        return (px / parentHeight * sHeight!!).toInt()
    }

    /**
     * dip转px
     * @param dipValue
     * @return int
     */
    fun dip2px(dipValue: Float): Int? {
        val scale = displayMetrics?.density
        return (dipValue * scale!! + 0.5f).toInt()
    }


}