package com.iwenchaos.koteye.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.mvp.contract.CategoryContract
import com.iwenchaos.koteye.mvp.model.bean.CategoryInfo
import com.iwenchaos.koteye.mvp.presenter.CategoryPresenter
import com.iwenchaos.koteye.net.exception.ErrorStatus
import com.iwenchaos.koteye.ui.adapter.CategoryAdapter
import com.iwenchaos.koteye.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * Created by chaos
 * on 2018/5/5. 15:32
 * 文件描述：
 */
class CategoryFragment : BaseFragment(), CategoryContract.View {

    private var dataList = ArrayList<CategoryInfo>()
    private var cateAdapter: CategoryAdapter? = null
    private val mPresenter by lazy {
        CategoryPresenter()
    }


    companion object {
        fun getInstance(bundle: Bundle?): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_category

    override fun initUi() {
        mPresenter.attachView(this)

        cateAdapter = CategoryAdapter(this.activity!!, dataList)
        cateRecyclerView.run {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = cateAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView, state: RecyclerView.State?) {
                    val posi = parent.getChildAdapterPosition(view)
                    val offset = DisplayManager.dip2px(2f)
                    val realOffset = if (posi % 2 == 0) 0 else offset
                    realOffset?.let { outRect?.set(it, realOffset, realOffset, realOffset) }
                }
            })
        }


    }

    override fun lazyLoad() {
        mPresenter.loadDta()
    }

    override fun showLoading() {
        multipleStatusView?.showLoading()
    }

    override fun closeLoading() {
        multipleStatusView?.showContent()
    }

    override fun renderUi(infoList: ArrayList<CategoryInfo>) {
        cateAdapter?.setData(infoList)


    }

    override fun showError(errorMsg: String, errorCode: Int) {
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView?.showNoNetwork()
        } else {
            multipleStatusView?.showError()
        }
    }
}