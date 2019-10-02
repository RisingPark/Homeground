package com.homeground.app.view.point.search


import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.homeground.app.R
import com.homeground.app.common.CommonOpener
import com.homeground.app.common.DialogHelper
import com.homeground.app.common.Utils
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.common.interfaces.OnItemClickListener
import com.homeground.app.common.ui.textview.ScaleTextView
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.point.save.PointSaveFragment
import com.homeground.app.view.point.search.adapter.UserListRecyclerViewAdapter
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.homeground.app.view.point.search.model.PointSearchViewModel
import kotlinx.android.synthetic.main.fragment_point_search.*
import kotlinx.android.synthetic.main.layout_cal_pad.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import kotlinx.android.synthetic.main.layout_number_pad.*
import kotlinx.android.synthetic.main.layout_number_pad.key_0
import kotlinx.android.synthetic.main.layout_number_pad.key_1
import kotlinx.android.synthetic.main.layout_number_pad.key_2
import kotlinx.android.synthetic.main.layout_number_pad.key_3
import kotlinx.android.synthetic.main.layout_number_pad.key_4
import kotlinx.android.synthetic.main.layout_number_pad.key_5
import kotlinx.android.synthetic.main.layout_number_pad.key_6
import kotlinx.android.synthetic.main.layout_number_pad.key_7
import kotlinx.android.synthetic.main.layout_number_pad.key_8
import kotlinx.android.synthetic.main.layout_number_pad.key_9
import kotlinx.android.synthetic.main.layout_number_pad.key_del
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PointSearchFragment : BaseFragment<FragmentUserInfoBinding, PointSearchViewModel>() {

    var resultNum :String = ""

    companion object {
        fun newInstance() = PointSearchFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_point_search
    override val vm : PointSearchViewModel by viewModel()
    private val mUserListRecyclerViewAdapter: UserListRecyclerViewAdapter by inject()

    override fun initStartView() {
        setToolbar()
        setNumberKeyPad()
    }

    override fun initDataBinding() {
        vm.userPointLiveData.observe(this, androidx.lifecycle.Observer {
            hideLoadingProgress()
            if (it.isSuccess){
                val isEmpty = Utils.isEmpty(it.userInfoResponseDTO)
                point_search_empty_layout.visibility = if (isEmpty) View.VISIBLE else View.GONE
                user_recycler_view.visibility = if (isEmpty) View.GONE else View.VISIBLE
                if(isEmpty) {
                    point_search_empty_text.text = getString(R.string.user_empty)
                    return@Observer
                }

                user_recycler_view.run {
                    adapter = mUserListRecyclerViewAdapter.apply {
                        this.items = it.userInfoResponseDTO as ArrayList<UserInfoResponseDTO>
                        onPointSaveClickListener = object :OnItemClickListener{
                            override fun onItemClickListener(view: View, position: Int) {
                                addFragment(PointSaveFragment.newInstance())
                            }
                        }
                        onPointUesClickListener = object :OnItemClickListener{
                            override fun onItemClickListener(view: View, position: Int) {

                            }
                        }
                        onPointHistoryClickListener = object :OnItemClickListener{
                            override fun onItemClickListener(view: View, position: Int) {

                            }
                        }
                    }
                }
            } else {
                DialogHelper.showCommonDialog(getBaseActivity(), it.msg, null)
            }
        })
    }

    override fun initAfterBinding() {
    }

    private fun setToolbar() {
        common_toolbar_title_text.text = getString(R.string.user_search)
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setNumberKeyPad() {
        val numBtn: Array<ScaleTextView> = arrayOf(key_0, key_1, key_2, key_3, key_4, key_5, key_6, key_7, key_8, key_9)
        for (view in numBtn) {
            view.setOnClickListener {
                if(resultNum.length > 3 ) return@setOnClickListener

                resultNum += view.text.toString()
                otp_view.otp = resultNum

                if(resultNum.length > 3) {
                    showLoadingProgress()
                    vm.getUserPoint(resultNum)
                }
            }
        }

        key_del.setOnClickListener {
            if (resultNum.isNotEmpty()){
                resultNum = resultNum.substring(0, resultNum.length-1)
                otp_view.otp = resultNum
            }
        }

        key_clear.setOnClickListener {
            if (resultNum.isNotEmpty()){
                resultNum = ""
                otp_view.otp = resultNum
            }
        }
    }
}
