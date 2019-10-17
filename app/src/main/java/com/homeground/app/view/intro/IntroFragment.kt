package com.homeground.app.view.intro


import android.animation.Animator
import android.os.Handler
import androidx.fragment.app.Fragment
import com.homeground.app.BuildConfig
import com.homeground.app.R
import com.homeground.app.common.CommonOpener
import com.homeground.app.common.Preference
import com.homeground.app.common.Utils
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentIntroBinding
import com.homeground.app.view.intro.model.IntroViewModel
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.coroutines.Runnable
import org.koin.androidx.viewmodel.ext.android.viewModel
import yanzhikai.textpath.PathAnimatorListener
import yanzhikai.textpath.calculator.AroundCalculator

/**
 * A simple [Fragment] subclass.
 */
class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>() {

    var runnable = Runnable {
        activity?.let { CommonOpener.openMainActivity(it) }
    }

    companion object {
        fun newInstance() = IntroFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_intro
    override val vm: IntroViewModel by viewModel()


    override fun initStartView() {
        if (!BuildConfig.DEBUG)
            setTextPathView()
        checkFirstLauncher()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(runnable)
    }

    // 텍스트 패스 뷰 설정
    private fun setTextPathView(){
        intro_text_path_view.apply {
            startAnimation(0f, 1F)
            setFillColor(true)
            setCalculator(AroundCalculator())
            setAnimatorListener(object : PathAnimatorListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mHandler.postDelayed(runnable, 500)
                }
            })
        }
    }

    private fun checkFirstLauncher(){
        activity?.let {
            if (Preference.isFirstLauncher(it)) {
                Preference.setDeviceName(it,"POS_"+(0..99).random())
                Preference.setFirstLauncher(it, false)
            }
        }

        if (BuildConfig.DEBUG)
            activity?.let { CommonOpener.openMainActivity(it) }
    }
}
