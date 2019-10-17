package com.homeground.app.common

import androidx.fragment.app.DialogFragment
import com.homeground.app.common.base.BaseActivity
import com.homeground.app.common.interfaces.OnDialogResultListener
import com.homeground.app.common.ui.dialog.CommonDialog

class DialogHelper {
    companion object{
        private fun showAllowingStateLoss(
            activity: BaseActivity?,
            fragment: DialogFragment,
            tag: String
        ) {
            activity?.let {
                if (!it.isFinishing){
                    try {
                        activity.supportFragmentManager.beginTransaction().add(fragment, tag)
                            .commitAllowingStateLoss()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }


        fun showCommonDialog(activity: BaseActivity?, msg: String?, onDialogResultListener: OnDialogResultListener?) {
            CommonDialog.newInstance(msg, onDialogResultListener).apply {
                showAllowingStateLoss(activity, this, this.javaClass.name)
            }
        }

        fun showCommonDialog(activity: BaseActivity?, msg: String?, leftBtnText: String?, onDialogResultListener: OnDialogResultListener?) {
            CommonDialog.newInstance(msg, leftBtnText, onDialogResultListener).apply {
                showAllowingStateLoss(activity, this, this.javaClass.name)
            }
        }

    }

}