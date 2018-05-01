package com.iwenchaos.koteye.glide


import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Pattern

/**
 * Created by chaos
 * on 2018/5/1. 13:38
 * 文件描述：
 */
class GildeUrlLoader(
        concreteLoader: ModelLoader<GlideUrl, InputStream>,
        modelCache: ModelCache<String, GlideUrl>)
    : BaseGlideUrlLoader<String>(concreteLoader, modelCache) {

    companion object {
        private val urlCache = ModelCache<String, GlideUrl>(100)
        private val PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__")
    }

    /**
     * If the URL contains a special variable width indicator (eg "__w-200-400-800__")
     * we get the buckets from the URL (200, 400 and 800 in the example) and replace
     * the URL with the best bucket for the requested width (the bucket immediately
     * larger than the requested width).
     *
     * 控制加载的图片的大小
     */
    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        val m = PATTERN.matcher(model)
        var bestBucket: Int
        if (m.find()) {
            val found = m.group(1).split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (bucket in found) {
                bestBucket = Integer.parseInt(bucket)
                if (bestBucket >= width) {
                    break
                }
            }
        }
        return model
    }
    /**
     * Returns true if the given model is a of a recognized type that this loader can probably load.
     *
     * <p> For example, you may want multiple Uri -> InputStream loaders. One might handle media
     * store Uris, another might handle asset Uris, and a third might handle file Uris etc. </p>
     *
     * <p> This method is generally expected to do no I/O and complete quickly, so best effort
     * results are acceptable. {@link ModelLoader ModelLoaders} that return true from this method may
     * return {@code null} from {@link #buildLoadData(Object, int, int, Options)} </p>
     */
    override fun handles(model: String): Boolean {
        return true
    }

    /**
    * 工厂来构建CustomBaseGlideUrlLoader对象
    */
    class Factory : ModelLoaderFactory<String, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return GildeUrlLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java), urlCache)
        }

        override fun teardown() {

        }
    }
}