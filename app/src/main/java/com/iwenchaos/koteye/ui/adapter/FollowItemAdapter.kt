package com.iwenchaos.koteye.ui.adapter

import android.content.Context
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.widget.recycler.CommonAdapter
import com.iwenchaos.koteye.widget.recycler.ViewHolder

/**
 * Created by chaos
 * on 2018/5/5. 16:48
 * 文件描述：
 */
class FollowItemAdapter(context: Context, categoryList: ArrayList<HomeInfo.Issue.Item>, layoutId: Int)
    : CommonAdapter<HomeInfo.Issue.Item>(context, categoryList, layoutId) {



    override fun bindData(holder: ViewHolder, data: HomeInfo.Issue.Item, position: Int) {

    }
}