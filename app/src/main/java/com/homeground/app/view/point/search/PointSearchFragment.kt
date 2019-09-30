package com.homeground.app.view.point.search


import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

import com.homeground.app.R
import com.homeground.app.common.DialogHelper
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.common.interfaces.OnDialogResultListener
import com.homeground.app.common.ui.textview.ScaleTextView
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.point.search.model.PointSearchViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_point_search.*
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class PointSearchFragment : BaseFragment<FragmentUserInfoBinding, PointSearchViewModel>() {

    var resultNum :String = "";

    companion object {
        fun newInstance() = PointSearchFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_point_search
    override val vm : PointSearchViewModel by viewModel()

    override fun initStartView() {
        setToolbar()
        setNumberKeyPad()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun setToolbar() {
        common_toolbar_title_text.text = getString(com.homeground.app.R.string.search)
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setNumberKeyPad() {
        val numBtn: Array<ScaleTextView> = arrayOf(key_0, key_1, key_2, key_3, key_4, key_5, key_6, key_7, key_8, key_9)
        for (view in numBtn) {
            view.setOnClickListener {
                resultNum += view.text.toString()
                pin_view.value = resultNum
            }
        }
    }
}
