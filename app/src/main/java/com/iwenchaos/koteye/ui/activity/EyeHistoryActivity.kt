package com.iwenchaos.koteye.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseActivity
import com.iwenchaos.koteye.base.EyeApplication
import com.iwenchaos.koteye.constant.Constants
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.ui.adapter.EyeHistoryAdapter
import com.iwenchaos.koteye.utils.WatchHistoryUtils
import kotlinx.android.synthetic.main.activity_eye_history.*
import java.util.*

/**
 * Created by chaos
 * on 2018/5/17. 16:05
 * 文件描述：
 */
class EyeHistoryActivity : BaseActivity() {

    private val HISTORY_MAX = 10


    var itemEyedList = ArrayList<HomeInfo.Issue.Item>()
    var eyedAdapter: EyeHistoryAdapter? = null

    override fun layoutId() = R.layout.activity_eye_history

    override fun initUi() {
        multipleStatusView.showLoading()

        eyedAdapter = EyeHistoryAdapter(this, itemEyedList, R.layout.item_eye_history_layout)
        hisRecyclerView.run {
            layoutManager = LinearLayoutManager(this@EyeHistoryActivity)
            adapter = eyedAdapter
        }


    }

    override fun loadDta() {
        val localList = queryLocalEyedVideo()
        localList?.let {
            eyedAdapter?.addDatas(it)
            multipleStatusView.showContent()
        } ?: let {
            multipleStatusView.showEmpty()
        }


    }


    private fun queryLocalEyedVideo(): ArrayList<HomeInfo.Issue.Item>? {
        val watchList = ArrayList<HomeInfo.Issue.Item>()
        val hisAll = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME, EyeApplication.context) as Map<*, *>
        //将key排序升序
        val keys = hisAll.keys.toTypedArray()
        Arrays.sort(keys)
        val keyLength = keys.size
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        val hisLength = if (keyLength > HISTORY_MAX) HISTORY_MAX else keyLength
        // 反序列化和遍历 添加观看的历史记录
        (1..hisLength).mapTo(watchList) {
            WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME, EyeApplication.context,
                    keys[keyLength - it] as String) as HomeInfo.Issue.Item
        }

        return watchList
    }
}