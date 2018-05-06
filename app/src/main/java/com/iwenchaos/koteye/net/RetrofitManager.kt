package com.iwenchaos.koteye.net

import com.iwenchaos.koteye.BuildConfig
import com.iwenchaos.koteye.base.EyeApplication
import com.iwenchaos.koteye.net.api.ApiService
import com.iwenchaos.koteye.utils.NetworkUtil
import com.iwenchaos.koteye.utils.Preference
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by chaos
 * on 2018/5/1. 15:44
 * 文件描述：
 */
object RetrofitManager {

    private var token: String by Preference("token", "")

    private var okClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    val service: ApiService by lazy {
        getRetrofit()!!.create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitManager::class.java) {
                if (retrofit == null) {
                    //log
                    val httpLogInterceptor = HttpLoggingInterceptor()
                    if (BuildConfig.DEBUG)
                        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    //cache
                    val cacheFile = File(EyeApplication.context.cacheDir, "net_cache")
                    val cache = Cache(cacheFile, 50 * 1024 * 1024)//50Mb

                    okClient = OkHttpClient.Builder()
                            .addInterceptor(httpLogInterceptor)
                            .addInterceptor(addHeaderInterceptor())
                            .addInterceptor(addCacheInterceptor())
                            .cache(cache)
                            .connectTimeout(60L, TimeUnit.SECONDS)
                            .readTimeout(60L, TimeUnit.SECONDS)
                            .writeTimeout(60L, TimeUnit.SECONDS)
                            .build()

                    retrofit = Retrofit.Builder()
                            .baseUrl("http://baobab.kaiyanapp.com/api/")
                            .client(okClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }

        }
        return retrofit
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(EyeApplication.context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(EyeApplication.context)) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }
}