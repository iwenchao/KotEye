package com.iwenchaos.koteye.ui.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
 * on 2018/5/17. 16:11
 * 文件描述：
 */
class EyeHistoryAdapter(context: Context, dataList: ArrayList<HomeInfo.Issue.Item>, layoutId: Int) : CommonAdapter<HomeInfo.Issue.Item>(context, dataList, layoutId) {


    fun addDatas(datas: ArrayList<HomeInfo.Issue.Item>?) {
        datas?.run {
            list.addAll(datas)
            notifyDataSetChanged()
        }

    }

    override fun bindData(holder: ViewHolder, data: HomeInfo.Issue.Item, position: Int) {
        with(holder) {
            setText(R.id.tv_title, data.data?.title!!)
            setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
            setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                override fun loadImage(iv: ImageView, path: String) {
                    GlideApp.with(context)
                            .load(path)
                            .placeholder(R.mipmap.placeholder_banner)
                            .transition(DrawableTransitionOptions().crossFade())
                            .into(iv)
                }
            })
        }
        holder.getView<TextView>(R.id.tv_title).setTextColor(context.resources.getColor(R.color.color_black))
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(context as Activity, holder.getView(R.id.iv_video_small_card), data)
        })
    }


    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeInfo.Issue.Item) {
        Intent(activity, VideoDetailActivity::class.java).run {
            putExtra(Constants.Key.BUNDLE_VIDEO_DATA, itemData)
            putExtra(Constants.Key.TRANSITION, true)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val pair = Pair<View, String>(view, Constants.Key.IMG_TRANSITION)
                val options = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions.makeSceneTransitionAnimation(activity, pair)
                } else {
                    null
                }
                ActivityCompat.startActivity(activity, this, options?.toBundle())
            } else {
                activity.startActivity(this)
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
            }
        }

    }
}