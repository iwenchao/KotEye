package com.iwenchaos.koteye.constant

/**
 * Created by chaos
 * on 2018/5/1. 14:51
 * 文件描述：
 */
class Constants private constructor(){
    companion object Key{

        val BUNDLE_VIDEO_DATA = "video_data"
        val BUNDLE_CATEGORY_DATA = "category_data"

        val IMG_TRANSITION = "IMG_TRANSITION"
        val TRANSITION = "TRANSITION"

        //腾讯 Bugly APP id
        val BUGLY_APPID = "176aad0d9e"


        //sp 存储的文件名
        val FILE_WATCH_HISTORY_NAME = "watch_history_file"   //观看记录

        val FILE_COLLECTION_NAME = "collection_file"    //收藏视屏缓存的文件名
    }
}