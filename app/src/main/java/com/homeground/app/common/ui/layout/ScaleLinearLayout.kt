package com.homeground.app.common.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class ScaleLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        scaleX = if (pressed) 0.9f else 1.0f
        scaleY = if (pressed) 0.9f else 1.0f
    }
}