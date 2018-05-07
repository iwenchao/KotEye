package com.iwenchaos.koteye.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.EyeApplication
import com.iwenchaos.koteye.durationFormat
import com.iwenchaos.koteye.glide.GlideApp
import com.iwenchaos.koteye.glide.RoundTransform
import com.iwenchaos.koteye.mvp.model.bean.HomeInfo
import com.iwenchaos.koteye.widget.recycler.CommonAdapter
import com.iwenchaos.koteye.widget.recycler.MultipleType
import com.iwenchaos.koteye.widget.recycler.ViewHolder

/**
 * Created by chaos
 * on 2018/5/7. 10:13
 * 文件描述：
 */
class VideoDetailAdapter(context: Context, dataList: ArrayList<HomeInfo.Issue.Item>)
    : CommonAdapter<HomeInfo.Issue.Item>(context, dataList, object : MultipleType<HomeInfo.Issue.Item> {
    override fun getLayoutId(item: HomeInfo.Issue.Item, position: Int): Int {
        return when {
            position == 0 -> {
                R.layout.item_video_detail_info
            }
            dataList[position].type == "textCard" ->
                R.layout.item_video_text_card

            dataList[position].type == "videoSmallCard" ->
                R.layout.item_video_small_card
            else ->
                throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

}) {

    private var textTF: Typeface? = null

    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */
    var onItemClickListenter: ((item: HomeInfo.Issue.Item) -> Unit)? = null

    init {
        textTF = Typeface.createFromAsset(EyeApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    fun addVideoInfo(info: HomeInfo.Issue.Item) {
        this.list.clear()
        notifyDataSetChanged()
        this.list.add(info)
        notifyItemInserted(0)

    }

    fun addVideoRelateList(list: ArrayList<HomeInfo.Issue.Item>) {
        this.list.addAll(list)
        notifyItemRangeInserted(1, list.size)
    }


    override fun bindData(holder: ViewHolder, data: HomeInfo.Issue.Item, position: Int) {
        when {
            position == 0 -> setVideoInfo(data, holder)
            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)
                //设置方正兰亭细黑简体
                holder.getView<TextView>(R.id.tv_text_card).typeface = textTF
            }
            data.type == "videoSmallCard" -> {
                with(holder) {
                    setText(R.id.tv_title, data.data?.title!!)
                    setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
                    setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                        override fun loadImage(iv: ImageView, path: String) {
                            GlideApp.with(context)
                                    .load(path)
                                    .optionalTransform(RoundTransform())
                                    .placeholder(R.mipmap.placeholder_banner)
                                    .into(iv)
                        }

                    })
                }
                holder.itemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {

                    }

                })
            }
            else -> throw IllegalAccessException("错误的数据结果" + data.type)

        }

    }

    private fun setVideoInfo(data: HomeInfo.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let { holder.setText(R.id.tv_title, it) }
        //视频简介
        data.data?.description?.let { holder.setText(R.id.expandable_text, it) }
        //标签
        holder.setText(R.id.tv_tag, "#${data.data?.category} / ${durationFormat(data.data?.duration)}")
        //喜欢
        holder.setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
        //分享
        holder.setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
        //评论
        holder.setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())

        if (data.data?.author != null) {
            with(holder) {
                setText(R.id.tv_author_name, data.data.author.name)
                setText(R.id.tv_author_desc, data.data.author.description)
                setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(data.data.author.icon) {
                    override fun loadImage(iv: ImageView, path: String) {
                        //加载头像
                        GlideApp.with(context)
                                .load(path)
                                .placeholder(R.mipmap.default_avatar).circleCrop()
                                .into(iv)
                    }
                })
            }
        } else {
            holder.setViewVisibility(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(EyeApplication.context, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(EyeApplication.context, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(EyeApplication.context, "评论", Toast.LENGTH_SHORT).show()
            }
        }
    }
}