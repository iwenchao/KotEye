package com.iwenchaos.koteye.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.mvp.model.bean.TabIVo
import com.iwenchaos.koteye.net.RetrofitManager
import com.iwenchaos.koteye.net.exception.ErrorStatus
import com.iwenchaos.koteye.net.exception.ExceptionHandle
import com.iwenchaos.koteye.ui.adapter.LocalPagerAdapter
import com.iwenchaos.koteye.utils.StatusBarUtil
import com.iwenchaos.koteye.utils.TabLayoutHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by chaos
 * on 2018/5/6. 10:59
 * 文件描述：
 */
class HotFragment : BaseFragment() {

    private var mTitle: String? = null
    private val tabTxtList = ArrayList<String>()
    private val tabPageList = ArrayList<Fragment>()
    private val composite = CompositeDisposable()

    companion object {
        fun getInstance(title: String, bundle: Bundle?): HotFragment {
            val fragment = HotFragment()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_hot

    override fun initUi() {
        StatusBarUtil.darkMode(activity!!)
        StatusBarUtil.setPaddingSmart(activity!!, hotToolbar)
    }

    /**
     * 此处不用MVP架构写法
     */
    override fun lazyLoad() {
        showLoading()
        val disposible = RetrofitManager.service.getHotTabList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<TabIVo> {
                    override fun accept(t: TabIVo?) {
                        closeLoading()
                        t?.let {
                            renderUI(t)
                        } ?: let {
                            showError(ExceptionHandle.handleException(IllegalArgumentException("数据为null")), ExceptionHandle.errorCode)
                        }
                    }

                }, Consumer<Throwable> {
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                })


        composite.add(disposible)
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun closeLoading() {
        multipleStatusView.showContent()
    }

    fun renderUI(info: TabIVo) {
        multipleStatusView.showContent()
        info.tabInfo.tabList.mapTo(tabTxtList) { it.name }
        info.tabInfo.tabList.mapTo(tabPageList) {
            CategoryFragment.getInstance(null)
        }

        hotViewPager.run {
            adapter = LocalPagerAdapter(childFragmentManager, tabPageList, tabTxtList)
        }
        hotTabLayout.setupWithViewPager(hotViewPager)
        TabLayoutHelper.setUpIndicatorWidth(hotTabLayout)

    }

    fun showError(errorMsg: String, errorCode: Int) {
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        composite.clear()
    }
}