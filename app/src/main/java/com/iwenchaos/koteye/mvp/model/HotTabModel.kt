package com.iwenchaos.koteye.mvp.model

import com.iwenchaos.koteye.mvp.contract.HotTabContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.net.RetrofitManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chaos
 * on 2018/5/6. 14:01
 * 文件描述：
 */
class HotTabModel : HotTabContract.Model {


    override fun loadDta(pageUrl: String): Observable<HomeInfo.Issue> {
        return RetrofitManager.service.getTabContent(pageUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}