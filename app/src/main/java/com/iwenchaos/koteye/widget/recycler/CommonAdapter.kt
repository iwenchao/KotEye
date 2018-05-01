package com.iwenchaos.koteye.widget.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by chaos
 * on 2018/5/1. 11:14
 * 文件描述：
 */
abstract class CommonAdapter<T>(open var context: Context,
                                var list: ArrayList<T>,
                                var layoutId: Int) : RecyclerView.Adapter<ViewHolder>() {
    val inflater by lazy {
        LayoutInflater.from(context)
    }

    private var typeSupport: MultipleType<T>? = null
    //使用接口回调点击事件
    var itemClickListener: OnItemClickListener? = null
    //使用接口回调点击事件
    var itemLongClickListener: OnItemLongClickListener? = null


    constructor(context: Context, list: ArrayList<T>, typeSupport: MultipleType<T>) : this(context, list, -1) {
        this.typeSupport = typeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (typeSupport != null) {
            layoutId = viewType
        }
        val itemView = inflater.inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return typeSupport?.getLayoutId(list[position], position) ?: super.getItemViewType(position)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindData(holder, list[position], position)

        itemClickListener?.let {
            holder?.itemView?.setOnClickListener { itemClickListener?.onItemClick(list[position], position) }
        }
        itemLongClickListener?.let {
            holder?.itemView?.setOnLongClickListener { itemLongClickListener?.onItemLongClick(list[position], position)!! }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    abstract fun bindData(holder: ViewHolder, data: T, position: Int)
}