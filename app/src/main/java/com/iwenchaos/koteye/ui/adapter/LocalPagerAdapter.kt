package com.iwenchaos.koteye.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by chaos
 * on 2018/5/5. 15:34
 * 文件描述：
 */
class LocalPagerAdapter constructor(val fm: FragmentManager?, var listFragment: List<Fragment>, val listTitles: List<String>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int) = listFragment[position]

    override fun getCount() = listFragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitles[position]
    }


}