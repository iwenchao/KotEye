package com.iwenchaos.koteye.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.util.Pair
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.constant.Constants
import com.iwenchaos.koteye.durationFormat
import com.iwenchaos.koteye.glide.GlideApp
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.ui.activity.MainActivity
import com.iwenchaos.koteye.widget.recycler.CommonAdapter
import com.iwenchaos.koteye.widget.recycler.ViewHolder
import io.reactivex.Observable


/**
 * Created by chaos
 * on 2018/5/1. 10:54
 * 文件描述：
 * 1. 构建adapter基类，抽象方便以后的效率
 * 2. 构造器
 * 3. 数据-》itemCount-》itemType-》viewHolder-》bindDta
 */
class HomeAdapter(override var context: Context, list: ArrayList<HomeInfo.Issue.Item>)
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

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER -> {
                ViewHolder(inflateView(R.layout.item_home_banner, parent))
            }
            ITEM_TYPE_TEXT_HEADER -> {
                ViewHolder(inflateView(R.layout.item_home_header, parent))
            }
            else -> {
                ViewHolder(inflateView(R.layout.item_home_content, parent))
            }
        }
    }

    private fun inflateView(layoutId: Int, parent: ViewGroup?): View {
        return inflater.inflate(layoutId, parent, false)
    }


    @SuppressLint("NewApi")
    override fun bindData(holder: ViewHolder, data: HomeInfo.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItem = list.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //提取banner 中的img与title信息
                Observable.fromIterable(bannerItem)
                        .subscribe({ list ->
                            bannerFeedList.add(list.data?.cover?.feed ?: "")
                            bannerTitleList.add(list?.data?.title ?: "")
                        })
                with(holder) {
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter(object : BGABanner.Adapter<ImageView, String> {
                            override fun fillBannerItem(bgaBanner: BGABanner?, imageView: ImageView, feedImageUrl: String?, position: Int) {
                                GlideApp.with(this@HomeAdapter.context)
                                        .load(feedImageUrl)
                                        .transition(DrawableTransitionOptions().crossFade())
                                        .placeholder(R.mipmap.placeholder_banner)
                                        .into(imageView)

                            }
                        })
                    }
                }
                holder.getView<BGABanner>(R.id.banner).setDelegate { _, imageView, _, i ->
                    gotoViewPlayer(this.context as Activity, imageView, bannerItem[i])
                }

            }
            ITEM_TYPE_TEXT_HEADER -> {
                holder.setText(R.id.tvHeader, list[position + bannerItemSize - 1].data?.text ?: "")
            }
            ITEM_TYPE_CONTENT -> {
                setVideoItem(holder, list[position + bannerItemSize - 1])
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun gotoViewPlayer(activity: Activity, view: View, itemData: HomeInfo.Issue.Item) {
        Intent(activity, MainActivity::class.java).run {
            putExtra(Constants.Key.BUNDLE_VIDEO_DATA, itemData)
            putExtra(Constants.Key.TRANSITION, true)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val pair = Pair<View, String>(view, Constants.Key.IMG_TRANSITION)
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, pair)
                ActivityCompat.startActivity(activity, this, options.toBundle())
            } else {
                activity.startActivity(this)
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
            }
        }
    }

    @SuppressLint("NewApi")
    private fun setVideoItem(holder: ViewHolder, item: HomeInfo.Issue.Item) {
        val itemData = item.data

        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        // 加载封页图
        GlideApp.with(this.context)
                .load(cover)
                .placeholder(R.mipmap.placeholder_banner)
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(viewId = R.id.iv_cover_feed))

        // 如果提供者信息为空，就显示默认
        if (avatar.isNullOrEmpty()) {
            GlideApp.with(this.context)
                    .load(defAvatar)
                    .placeholder(R.mipmap.default_avatar).circleCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))

        } else {
            GlideApp.with(this.context)
                    .load(avatar)
                    .placeholder(R.mipmap.default_avatar).circleCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))
        }
        holder.setText(R.id.tv_title, itemData?.title?:"")

        //遍历标签
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.setText(R.id.tv_tag, tagText!!)

        holder.setText(R.id.tv_category, "#" + itemData?.category)

        holder.setOnItemClickListener(listener = View.OnClickListener {
            gotoViewPlayer(this.context as Activity, holder.getView(R.id.iv_cover_feed), item)
        })


    }
}