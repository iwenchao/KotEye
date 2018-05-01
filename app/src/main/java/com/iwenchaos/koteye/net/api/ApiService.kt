package com.iwenchaos.koteye.net.api

import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by chaos
 * on 2018/5/1. 15:47
 * 文件描述：
 */
interface ApiService{

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int?): Observable<HomeInfo>
}