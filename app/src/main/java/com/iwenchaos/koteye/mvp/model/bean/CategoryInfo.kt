package com.iwenchaos.koteye.mvp.model.bean

import java.io.Serializable

/**
 * Created by chaos
 * desc:分类 Bean
 */
data class CategoryInfo(val id: Long, val name: String, val description: String, val bgPicture: String, val bgColor: String, val headerImage: String) : Serializable
