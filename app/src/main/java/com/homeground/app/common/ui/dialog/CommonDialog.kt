package com.homeground.app.common.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.homeground.app.R
import com.homeground.app.common.interfaces.OnDialogResultListener
import kotlinx.android.synthetic.main.layout_common_dialog.*

class CommonDialog : DialogFragment() {

    lateinit var msg: String
    var onDialogResultListener: OnDialogResultListener? = null


    companion object {
        const val KEY_MSG = "key_msg"

        fun newInstance(msg: String?, onDialogResultListener: OnDialogResultListener?) = CommonDialog().apply{
            onDialogResultListener?.let { this.onDialogResultListener = onDialogResultListener }
            arguments = Bundle().apply {
                putString(KEY_MSG, msg)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            msg = it.getString(KEY_MSG, "")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_common_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setMsg()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onDialogResultListener?.resultCancel()
    }

    private fun setMsg() {
        dialog_msg_text.text = msg
        dialog_confirm_btn.setOnClickListener {
            onDialogResultListener?.resultSuccess("")
            dismissAllowingStateLoss()
        }
    }

}