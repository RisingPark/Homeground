package com.homeground.app.view.point.save


import androidx.fragment.app.Fragment

import com.homeground.app.R
import com.homeground.app.common.Utils
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.common.ui.textview.ScaleTextView
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.point.save.model.PointSaveViewModel
import kotlinx.android.synthetic.main.fragment_point_save.*

import kotlinx.android.synthetic.main.layout_common_toolbar.*
import kotlinx.android.synthetic.main.layout_cal_pad.*
import kotlinx.android.synthetic.main.layout_cal_pad.key_0
import kotlinx.android.synthetic.main.layout_cal_pad.key_1
import kotlinx.android.synthetic.main.layout_cal_pad.key_2
import kotlinx.android.synthetic.main.layout_cal_pad.key_3
import kotlinx.android.synthetic.main.layout_cal_pad.key_4
import kotlinx.android.synthetic.main.layout_cal_pad.key_5
import kotlinx.android.synthetic.main.layout_cal_pad.key_6
import kotlinx.android.synthetic.main.layout_cal_pad.key_7
import kotlinx.android.synthetic.main.layout_cal_pad.key_8
import kotlinx.android.synthetic.main.layout_cal_pad.key_9
import kotlinx.android.synthetic.main.layout_cal_pad.key_del
import kotlinx.android.synthetic.main.layout_number_pad.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PointSaveFragment : BaseFragment<FragmentUserInfoBinding, PointSaveViewModel>() {

    var resultNum :String = ""

    companion object {
        fun newInstance() = PointSaveFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_point_save
    override val vm: PointSaveViewModel by viewModel()

    override fun initStartView() {
        setToolbar()
        setNumberKeyPad()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun setToolbar() {
        common_toolbar_title_text.text = getString(R.string.save)
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setNumberKeyPad() {
        val numBtn: Array<ScaleTextView> = arrayOf(key_0, key_00, key_1, key_2, key_3, key_4, key_5, key_6, key_7, key_8, key_9)
        for (view in numBtn) {
            view.setOnClickListener {
                if (resultNum.length > 10
                    || (resultNum == ""
                    && (view.text.toString() == "0" || view.text.toString() == "00"))) return@setOnClickListener

                resultNum += view.text.toString()
                point_money_text.text = Utils.addComma(resultNum.toLong()) + " 원"
                point_num_money_text.text = calPoint(resultNum)
            }
        }

        key_del.setOnClickListener {
            if (resultNum.isNotEmpty()){
                resultNum = resultNum.substring(0, resultNum.length-1)
                point_money_text.text = if (resultNum.isNotEmpty()) Utils.addComma(resultNum.toLong()) + " 원" else "0 원"
                point_num_money_text.text = if (resultNum.isNotEmpty()) calPoint(resultNum) else "0 P"
            }
        }

        key_fun_1.setOnClickListener {
            if (resultNum.isNotEmpty()){
                resultNum = ""
                point_money_text.text = "0 원"
                point_num_money_text.text = "0 P"
            }
        }
    }

    private fun calPoint(num:String) : String {
        if (num.isEmpty()) return ""

        return  Utils.addCommaP((num.toDouble() * 0.05).toLong())
    }
}
