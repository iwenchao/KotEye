package com.iwenchaos.koteye.mvp.model

import com.iwenchaos.koteye.mvp.contract.CategoryContract
import com.iwenchaos.koteye.mvp.model.bean.CategoryInfo
import com.iwenchaos.koteye.net.RetrofitManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chaos
 * on 2018/5/6. 10:17
 * 文件描述：
 */
class CategoryModel : CategoryContract.Model {


    override fun loadDta(): Observable<ArrayList<CategoryInfo>> {
        return RetrofitManager.service.getCategoryList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}