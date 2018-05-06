package com.iwenchaos.koteye.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.mvp.contract.HotTabContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.mvp.presenter.HotTabPresenter
import com.iwenchaos.koteye.ui.adapter.HotTabAdapter
import kotlinx.android.synthetic.main.fragment_hot_tab.*

/**
 * Created by chaos
 * on 2018/5/6. 13:53
 * 文件描述：
 */
class HotTabFragment : BaseFragment(), HotTabContract.View {



    private var pageUrl: String? = null
    private var mAdapter: HotTabAdapter? = null
    private var dataList = ArrayList<HomeInfo.Issue.Item>()
    private val mPresenter by lazy {
        HotTabPresenter()
    }

    companion object {
        fun getInstance(pageUrl: String?): HotTabFragment {
            val fragment = HotTabFragment()
            fragment.pageUrl = pageUrl
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_hot_tab

    override fun initUi() {
        mPresenter.attachView(this)
        mAdapter = HotTabAdapter(activity!!, dataList)
        tabRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

    }

    override fun lazyLoad() {
        pageUrl?.let { mPresenter.loadDta(it) }
    }

    override fun renderUi(data: HomeInfo.Issue) {
        mAdapter?.addData(data.itemList)


    }
    override fun showError(errorMsg: String, errorCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}