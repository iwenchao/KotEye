package com.iwenchaos.koteye.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.constant.Constants
import com.iwenchaos.koteye.durationFormat
import com.iwenchaos.koteye.glide.GlideApp
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.ui.activity.VideoDetailActivity
import com.iwenchaos.koteye.widget.recycler.CommonAdapter
import com.iwenchaos.koteye.widget.recycler.ViewHolder

/**
 * Created by chaos
 * on 2018/5/6. 14:08
 * 文件描述：
 */
class HotTabAdapter(context: Context, dataList: ArrayList<HomeInfo.Issue.Item>)
    : CommonAdapter<HomeInfo.Issue.Item>(context, dataList, R.layout.item_tab_detail) {

    fun addData(list: ArrayList<HomeInfo.Issue.Item>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, item: HomeInfo.Issue.Item, position: Int) {
        val itemData = item.data
        val cover = itemData?.cover?.feed ?: ""
        // 加载封页图
        GlideApp.with(context)
                .load(cover)
                .apply(RequestOptions().placeholder(R.mipmap.placeholder_banner))
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_image))
        holder.setText(R.id.tv_title, itemData?.title ?: "")

        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        holder.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(context as Activity, holder.getView(R.id.iv_image), item)
        })

    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeInfo.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(Constants.Key.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = android.support.v4.util.Pair<View, String>(view, Constants.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}