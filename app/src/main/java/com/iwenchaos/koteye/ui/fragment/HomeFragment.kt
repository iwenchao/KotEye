package com.iwenchaos.koteye.ui.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.mvp.contract.HomeContract
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.mvp.presenter.HomePresenter
import com.iwenchaos.koteye.ui.adapter.HomeAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by chaos
 * on 2018/5/1. 09:27
 * 文件描述：
 */
class HomeFragment : BaseFragment(), HomeContract.View {


    private var mTitle: String? = null
    private var isLoadingMore = false;
    private var isRefresh = false
    private var localHeader: MaterialHeader? = null
    private var homeAdapter: HomeAdapter? = null
    private val mPresenter by lazy { HomePresenter() }

    companion object {
        //伴随对象 获取fragment实例
        fun getInstance(title: String): HomeFragment {
            val f = HomeFragment()
            f.mTitle = title
            return f
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }
    private val dateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.CHINA)
    }

    override fun getLayoutId() = R.layout.fragment_home


    override fun initUi() {
        mPresenter.attachView(this)
        home_refresh_layout.run {
            setEnableHeaderTranslationContent(true)//设置是否启用内容视图拖动效果
            setOnLoadmoreListener {
                mPresenter.loadHomeDta()
            }
            setOnRefreshListener {
                isRefresh = true
                mPresenter.loadHomeDta()
            }
            setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)
        }
        home_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = home_recycler.childCount
                    val itemCount = home_recycler.layoutManager.itemCount
                    val firstVisiblePos = (home_recycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisiblePos + childCount == itemCount) {
                        if (!isLoadingMore) {
                            isLoadingMore = true
                            mPresenter.loadMoreDta()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val curVisibleItemPos = linearLayoutManager.findFirstVisibleItemPosition()
                if (curVisibleItemPos == 0) {//位于顶部，则透明显示
                    home_toolbar.setBackgroundColor(activity?.resources?.getColor(R.color.color_translucent)!!)
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (homeAdapter?.list!!.size > 1) {
                        home_toolbar.setBackgroundColor(activity?.resources?.getColor(R.color.color_title_bg)!!)
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = homeAdapter!!.list
                        val item = itemList[curVisibleItemPos + homeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = dateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })

        homeAdapter = HomeAdapter(activity)
        home_recycler.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun lazyLoad() {
        mPresenter.loadHomeDta()

    }

    override fun setBanner(banner: HomeInfo) {
        homeAdapter?.addBanner(banner.issueList[0].itemList)
    }

    override fun setContent(homeInfo: HomeInfo) {

        homeAdapter?.bannerItemSize = homeInfo.issueList[0].count
        homeAdapter?.addData(homeInfo.issueList[0].itemList)
    }


    override fun closeLoading() {
        super.closeLoading()
        isRefresh = false
        home_refresh_layout.finishRefresh()
        home_refresh_layout.finishLoadmore()
    }
}