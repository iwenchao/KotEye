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
/**
 * 数据流量格式化
 */
fun Context.dataformat(total: Long): String {
    var result: String
    var speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}
