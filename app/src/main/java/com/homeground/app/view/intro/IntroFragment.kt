package com.homeground.app.view.intro


import android.animation.Animator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.homeground.app.R
import com.homeground.app.common.CommonOpener
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentIntroBinding
import com.homeground.app.viewmodel.IntroViewModel
import kotlinx.android.synthetic.main.fragment_intro.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import yanzhikai.textpath.PathAnimatorListener
import yanzhikai.textpath.calculator.AroundCalculator
import yanzhikai.textpath.calculator.MidCalculator
import yanzhikai.textpath.painter.ArrowPainter
import yanzhikai.textpath.painter.FireworksPainter

/**
 * A simple [Fragment] subclass.
 */
class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>() {

    companion object {
        fun newInstance() = IntroFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_intro
    override val viewModel: IntroViewModel by viewModel()


    override fun initStartView() {
        setTextPathView()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
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
                    activity?.let { CommonOpener.openMainActivity(it) }
                }
            })
        }
    }
}
