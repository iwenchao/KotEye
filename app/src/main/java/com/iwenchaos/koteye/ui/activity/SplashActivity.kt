package com.iwenchaos.koteye.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.iwenchaos.koteye.R
import com.iwenchaos.koteye.base.BaseActivity
import com.iwenchaos.koteye.base.EyeApplication
import com.iwenchaos.koteye.toast
import com.iwenchaos.koteye.utils.AppUtil
import kotlinx.android.synthetic.main.activity_splash.*
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem

/**
 * Created by chaos
 * on 2018/4/20. 10:56
 * 文件描述：
 */
class SplashActivity : BaseActivity() {


    private var textTypeface: Typeface? = null
    private var descTypeface: Typeface? = null
    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeface = Typeface.createFromAsset(EyeApplication.context.assets, "fonts/Lobster-1.4.otf")
        descTypeface = Typeface.createFromAsset(EyeApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    /**
     * 布局
     */
    override fun layoutId(): Int = R.layout.activity_splash

    @SuppressLint("SetTextI18n")
    override fun initUi() {
        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeface
        tv_version_name.text = "v${AppUtil.getVerName(this)}"

        //渐变展示启动页
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                toMain()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        checkPermission()
    }

    override fun loadDta() {}

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkPermission() {
        val permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(PermissionItem(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.phone_storage), R.drawable.permission_ic_storage))
        HiPermission.create(this)
                .title(getString(R.string.honey))
                .msg(getString(R.string.please_open_permission))
                .permissions(permissionItems)
                .style(R.style.PermissionDefaultBlueStyle)
                .animStyle(R.style.PermissionAnimScale)
                .checkMutiPermission(object : PermissionCallback {
                    override fun onFinish() {
                        toast(getString(R.string.init_ok))
                    }

                    override fun onDeny(permission: String?, position: Int) {
                    }

                    override fun onGuarantee(permission: String?, position: Int) {
                    }

                    override fun onClose() {
                        toast(getString(R.string.permission_closed))
                    }
                })

    }

}