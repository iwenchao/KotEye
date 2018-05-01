package com.iwenchaos.koteye.glide

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.iwenchaos.koteye.base.EyeApplication
import java.security.MessageDigest

/**
 * Created by chaos
 * on 2018/5/1. 14:33
 * 文件描述：
 * 1.永远不要把transform()传给你的原始resource或原始Bitmap给recycle()了，更不要放回BitmapPool，因为这些都自动完成了。
 * 值得注意的是，任何从BitmapPool取出的用于自定义图片变换的辅助Bitmap，如果不经过transform()方法返回，就必须主动放回BitmapPool或者调用recycle()回收。
 *
 * 2.如果你从BitmapPool拿出多个Bitmap或不使用你从BitmapPool拿出的一个Bitmap，一定要返回extras给BitmapPool。
 *
 * 3.如果你的图片处理没有替换原始resource(例如由于一张图片已经匹配了你想要的尺寸，你需要提前返回), transform()`方法就返回原始resource或原始Bitmap。
 *
 */
class RoundTransform @JvmOverloads constructor(context: Context = EyeApplication.context, dp: Int = 4) : BitmapTransformation(context) {

    private var radius = 0f

    init {
        this.radius = Resources.getSystem().displayMetrics.density * dp
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return roundTransform(pool, toTransform)
    }

    private fun roundTransform(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) return null
        var result: Bitmap? = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        }

        var canvas = Canvas(result)
        var paint = Paint()
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radius, radius, paint)
        return result
    }

}