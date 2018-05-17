package com.iwenchaos.koteye.ui.fragment

import android.content.Intent
import android.view.View
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseFragment
import com.iwenchaos.koteye.ui.activity.EyeHistoryActivity
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * Created by chaos
 * on 2018/5/3. 14:44
 * 文件描述：
 */
class UserFragment : BaseFragment(), View.OnClickListener {


    private var mTitle: String? = null

    companion object {
        //伴随对象 获取fragment实例
        fun getInstance(title: String): UserFragment {
            val f = UserFragment()
            f.mTitle = title
            return f
        }
    }


    override fun getLayoutId() = R.layout.fragment_user

    override fun initUi() {

        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        app_bar.addOnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset <= -(login_layout.height / 2)) {
                collapsing_toolbar.title = "master"
            } else {
                collapsing_toolbar.title = " "
            }
        }

        tv_watch_history.setOnClickListener(this)

    }

    override fun lazyLoad() {

    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.tv_watch_history -> {
                Intent(activity, EyeHistoryActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
    }

}