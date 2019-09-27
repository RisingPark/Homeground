package com.homeground.app.common.ui.imageview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class ScaleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        scaleX = if (pressed) 0.9f else 1.0f
        scaleY = if (pressed) 0.9f else 1.0f

    }


}