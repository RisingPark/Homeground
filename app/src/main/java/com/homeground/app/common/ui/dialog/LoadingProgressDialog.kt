package com.homeground.app.common.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatDialog
import com.airbnb.lottie.LottieDrawable
import com.homeground.app.R
import kotlinx.android.synthetic.main.layout_loading_progress.*

class LoadingProgressDialog : AppCompatDialog {

    private val progressBar: ProgressBar? = null
    private var imageView: ImageView? = null
    private var drawable: AnimationDrawable? = null

    constructor(context: Context) : super(context) {
        setCancelable(false)
        setContentView(R.layout.layout_loading_progress)
        initComponent()
    }

    constructor(context: Context, cancelable: Boolean) : super(context) {
        setCancelable(cancelable)
        setContentView(R.layout.layout_loading_progress)
        initComponent()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lottie_view.cancelAnimation()
    }


    /**
     * 화면 구성에 필요한 컴포넌트 초기화.
     */
    private fun initComponent() {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        lottie_view.apply {
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
    }
}