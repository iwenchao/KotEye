package com.iwenchaos.koteye.mvp.model

import com.iwenchaos.koteye.mvp.contract.HomeContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.net.RetrofitManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chaos
 * on 2018/4/30. 15:54
 * 文件描述：
 */
class HomeModel : HomeContract.Model {







    override fun loadHomeDta(page:Int?): Observable<HomeInfo> {
        return RetrofitManager.service.getFirstHomeData(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    override fun loadMoreDta() {

    }


}