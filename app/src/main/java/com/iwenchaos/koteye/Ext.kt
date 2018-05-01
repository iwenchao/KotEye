package com.iwenchaos.koteye

import android.content.Context
import android.widget.Toast
import com.iwenchaos.koteye.base.EyeApplication

/**
 * Created by chaos
 * on 2018/4/19. 16:12
 * 文件描述：
 */
val EYE_APPLICATION: String = "EyeApplication"

fun Context.toast(content:String) {
    Toast.makeText(EyeApplication.context,content,Toast.LENGTH_SHORT).show()
}

