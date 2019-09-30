package com.homeground.app.common.ui.textview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class ScaleTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        scaleX = if (pressed) 0.9f else 1.0f
        scaleY = if (pressed) 0.9f else 1.0f
    }
}