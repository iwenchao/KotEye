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


fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}
