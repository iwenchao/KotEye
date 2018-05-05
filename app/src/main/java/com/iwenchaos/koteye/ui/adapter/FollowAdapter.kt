package com.iwenchaos.koteye.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.glide.GlideApp
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.widget.recycler.CommonAdapter
import com.iwenchaos.koteye.widget.recycler.MultipleType
import com.iwenchaos.koteye.widget.recycler.ViewHolder

/**
 * Created by chaos
 * on 2018/5/5. 16:23
 * 文件描述：
 */
class FollowAdapter(context: Context, dataList: ArrayList<HomeInfo.Issue.Item>)
    : CommonAdapter<HomeInfo.Issue.Item>(context, dataList, object : MultipleType<HomeInfo.Issue.Item> {
    override fun getLayoutId(item: HomeInfo.Issue.Item, position: Int): Int {
        return when {
            item.type == "videoCollectionWithBrief" -> R.layout.item_follow
            else -> throw IllegalArgumentException("不支持的数据类型" + item.type)
        }
    }

}) {


    fun addData(dataList: ArrayList<HomeInfo.Issue.Item>) {
        this.list.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: HomeInfo.Issue.Item, position: Int) {
        when {
            data.type == "videoCollectionWithBrief" -> bindItemData(holder, data)
        }
    }


    private fun bindItemData(holder: ViewHolder, item: HomeInfo.Issue.Item) {
        val headerData = item.data?.header
        headerData?.let {
            holder.setText(R.id.tv_title, it.title)
            holder.setText(R.id.tv_desc, it.description)
            holder.setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(headerData.icon) {
                override fun loadImage(iv: ImageView, path: String) {
                    GlideApp.with(context)
                            .load(path)
                            .placeholder(R.mipmap.default_avatar)
                            .circleCrop()
                            .transition(DrawableTransitionOptions().crossFade())
                            .into(holder.getView(R.id.iv_avatar))
                }

            })
        }


        val recyclerView = holder.getView<RecyclerView>(R.id.fl_recyclerView)
        recyclerView.run {
            layoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = FollowItemAdapter(context, item.data?.itemList!!,R.layout.item_follow_horizontal)
        }


    }

}