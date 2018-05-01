package com.iwenchaos.koteye.ui.adapter

import android.content.Context
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.widget.recycler.CommonAdapter
import com.iwenchaos.koteye.widget.recycler.ViewHolder
import io.reactivex.Observable

/**
 * Created by chaos
 * on 2018/5/1. 10:54
 * 文件描述：
 */
class HomeAdapter(context: Context, list: ArrayList<HomeInfo.Issue.Item>)
    : CommonAdapter<HomeInfo.Issue.Item>(context, list, -1) {


    var bannerItemSize = 0

    companion object {
        private val ITEM_TYPE_BANNER = 1
        private val ITEM_TYPE_TEXT_HEADER = 2
        private val ITEM_TYPE_CONTENT = 3
    }


    fun addData(itemList: ArrayList<HomeInfo.Issue.Item>) {
        this.list.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return when {
            list.size > bannerItemSize -> list.size - bannerItemSize + 1
            list.isEmpty() -> 0
            else -> 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            list[position + bannerItemSize - 1].type == "textHeader" -> ITEM_TYPE_TEXT_HEADER
            else -> ITEM_TYPE_CONTENT
        }
    }


    override fun bindData(holder: ViewHolder?, data: HomeInfo.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItem  = list.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //提取banner 中的img与title信息
                Observable.fromIterable(bannerItem)
                        .subscribe({ list ->
                            bannerFeedList.add(list.data?.cover?.feed?:"")
                            bannerTitleList.add(list?.data?.title?:"")
                        })
                with(holder){
                    getView(BGBa)
                }

            }
            ITEM_TYPE_TEXT_HEADER -> {

            }
            ITEM_TYPE_CONTENT -> {

            }
        }
    }
}