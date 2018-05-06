package com.iwenchaos.koteye.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.constant.Constants
import com.iwenchaos.koteye.durationFormat
import com.iwenchaos.koteye.glide.GlideApp
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.ui.activity.VideoDetailActivity
import com.iwenchaos.koteye.widget.recycler.CommonAdapter
import com.iwenchaos.koteye.widget.recycler.ViewHolder
import com.orhanobut.logger.Logger

/**
 * Created by chaos
 * on 2018/5/5. 16:48
 * 文件描述：
 */
class FollowItemAdapter(context: Context, categoryList: ArrayList<HomeInfo.Issue.Item>, layoutId: Int)
    : CommonAdapter<HomeInfo.Issue.Item>(context, categoryList, layoutId) {


    override fun bindData(holder: ViewHolder, data: HomeInfo.Issue.Item, position: Int) {
        val followItemData = data.data
        holder.setImagePath(R.id.iv_cover_feed, object : ViewHolder.HolderImageLoader(data.data?.cover?.feed!!) {
            override fun loadImage(iv: ImageView, path: String) {
                // 加载封页图
                GlideApp.with(context)
                        .load(path)
                        .placeholder(R.mipmap.placeholder_banner)
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(holder.getView(R.id.iv_cover_feed))
            }

        })

        //横向 RecyclerView 封页图下面标题
        holder.setText(R.id.tv_title, followItemData?.title ?: "")

        // 格式化时间
        val timeFormat = durationFormat(followItemData?.duration)
        //标签
        with(holder) {
            Logger.d("FollowItemAdapter===title:${followItemData?.title}tag:${followItemData?.tags?.size}")

            if (followItemData?.tags != null && followItemData.tags.size > 0) {
                setText(R.id.tv_tag, "#${followItemData.tags[0].name} / $timeFormat")
            } else {
                setText(R.id.tv_tag, "#$timeFormat")
            }

            setOnItemClickListener(listener = View.OnClickListener {
                goToVideoPlayer(context as Activity, holder.getView(R.id.iv_cover_feed), data)
            })
        }

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