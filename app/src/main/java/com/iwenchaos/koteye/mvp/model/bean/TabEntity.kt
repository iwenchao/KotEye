package com.iwenchaos.koteye.mvp.model.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Created by chaos
 * on 2018/4/30. 15:52
 * 文件描述：
 */
class TabEntity(
        var title: String,
        var selectedIcon: Int,
        var unSelectedIcon: Int
) : CustomTabEntity {
    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}