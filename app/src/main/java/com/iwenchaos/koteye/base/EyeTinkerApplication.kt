package com.iwenchaos.koteye.base

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants


/**
 * Created by chaos
 * on 2018/4/22. 20:28
 * 文件描述：在kotlin中 tinker的注解生成器貌似不管用，需要手动创建
 */
class EyeTinkerApplication : TinkerApplication {
    constructor() : super(
            //tinkerFlags, tinker支持的类型，dex,library，还是全部都支持！
            ShareConstants.TINKER_ENABLE_ALL,
            //ApplicationLike的实现类，只能传递字符串
            "com.iwenchaos.koteye.base.EyeApplication",
            //Tinker的加载器，一般来说用默认的即可
            "com.tencent.tinker.loader.TinkerLoader",
            //tinkerLoadVerifyFlag, 运行加载时是否校验dex与,ib与res的Md5
            false)

}