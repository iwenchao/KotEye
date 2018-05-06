package com.iwenchaos.koteye.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.EyeApplication
import com.iwenchaos.koteye.constant.Constants
import com.iwenchaos.koteye.glide.GlideApp
import com.iwenchaos.koteye.mvp.model.bean.CategoryInfo
import com.iwenchaos.koteye.ui.activity.CategoryDetailActivity
import com.iwenchaos.koteye.widget.recycler.CommonAdapter
import com.iwenchaos.koteye.widget.recycler.ViewHolder

/**
 * Created by chaos
 * on 2018/5/6. 10:23
 * 文件描述：
 */
class CategoryAdapter(context: Context, var dataList: ArrayList<CategoryInfo>)
    : CommonAdapter<CategoryInfo>(context, dataList, R.layout.item_category) {

    private var textTF: Typeface? = null

    init {
        textTF = Typeface.createFromAsset(EyeApplication.context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }


    fun setData(categoryList: ArrayList<CategoryInfo>?) {
        categoryList?.let {
            dataList.clear()
            dataList.addAll(categoryList)
            notifyDataSetChanged()
        }

    }


    override fun bindData(holder: ViewHolder, data: CategoryInfo, position: Int) {
        holder.setText(R.id.tv_category_name, "#${data.name}")
        //设置方正兰亭细黑简体
        holder.getView<TextView>(R.id.tv_category_name).typeface = textTF
        holder.setImagePath(R.id.iv_category, object : ViewHolder.HolderImageLoader(data.bgPicture) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideApp.with(context)
                        .load(path)
                        .placeholder(R.color.color_darker_gray)
                        .transition(DrawableTransitionOptions().crossFade())
                        .thumbnail(0.5f)
                        .into(iv)
            }
        })

        holder.setOnItemClickListener(View.OnClickListener {
            val intent = Intent(context as Activity, CategoryDetailActivity::class.java)
            intent.putExtra(Constants.BUNDLE_CATEGORY_DATA, data)
            context.startActivity(intent)
        })

    }
}