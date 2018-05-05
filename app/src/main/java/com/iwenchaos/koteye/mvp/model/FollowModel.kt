package com.iwenchaos.koteye.mvp.model

import com.iwenchaos.koteye.mvp.contract.FollowContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.net.RetrofitManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chaos
 * on 2018/5/5. 16:12
 * 文件描述：
 */
class FollowModel : FollowContract.Model {


    override fun loadDta(): Observable<HomeInfo.Issue> {
        return RetrofitManager.service.getFollowList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    override fun loadMore(pageUrl: String?): Observable<HomeInfo.Issue> {
        return RetrofitManager.service.getMoreIssue(pageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }
}