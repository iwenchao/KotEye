package com.iwenchaos.koteye.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.mvp.contract.FollowContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.mvp.presenter.FollowPresenter
import com.iwenchaos.koteye.ui.adapter.FollowAdapter
import kotlinx.android.synthetic.main.fragment_follow.*

/**
 * Created by chaos
 * on 2018/5/5. 15:32
 * 文件描述：
 */
class FollowFragment : BaseFragment(), FollowContract.View {


    private var dataList = ArrayList<HomeInfo.Issue.Item>()
    private val mPresenter by lazy {
        FollowPresenter()
    }
    private var mFollowAdapter: FollowAdapter? = null

    companion object {

        fun getInstance(bundle: Bundle?): FollowFragment {
            val fragment = FollowFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_follow

    override fun initUi() {
        mPresenter.attachView(this)
        mFollowAdapter = FollowAdapter(this.activity!!, dataList)
        folRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = mFollowAdapter
        }

    }

    override fun lazyLoad() {
        mPresenter.loadDta()
    }

    override fun renderUi(issue: HomeInfo.Issue?) {
        issue?.itemList?.let {
            mFollowAdapter?.addData(it)
        }


    }

    override fun showError(errorMsg: String, errorCode: Int) {

    }
}