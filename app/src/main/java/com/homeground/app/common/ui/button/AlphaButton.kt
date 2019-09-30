package com.homeground.app.common.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.Button

class AlphaButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr){

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        alpha = if (pressed) 0.6f else 1.0f
    }

}