package com.iwenchaos.koteye.net.api

import com.iwenchaos.koteye.mvp.model.bean.CategoryInfo
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.mvp.model.bean.TabIVo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by chaos
 * on 2018/5/1. 15:47
 * 文件描述：
 */
interface ApiService {

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int?): Observable<HomeInfo>

    /**
     * 首页数据 nextPageUrl
     */
    @GET
    fun getFirstHomeData(@Url url: String): Observable<HomeInfo>

    @GET("v4/tabs/follow")
    fun getFollowList(): Observable<HomeInfo.Issue>


    @GET
    fun getMoreIssue(@Url url: String?): Observable<HomeInfo.Issue>

    @GET("v4/categories")
    fun getCategoryList(): Observable<ArrayList<CategoryInfo>>

    @GET("v4/rankList")
    fun getHotTabList(): Observable<TabIVo>


    @GET
    fun getTabContent(@Url pageUrl: String): Observable<HomeInfo.Issue>


}