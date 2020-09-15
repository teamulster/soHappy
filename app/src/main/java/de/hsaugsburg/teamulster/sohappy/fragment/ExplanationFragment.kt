package de.hsaugsburg.teamulster.sohappy.fragment

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentExplanationBinding

/**
 * ExplanationFragment contains instructions on how to use the app.
 */
class ExplanationFragment : Fragment() {
    private lateinit var binding: FragmentExplanationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_explanation,
            container,
            false
        )
        val pages = listOf(
            R.layout.page_explanation_01, R.layout.page_explanation_02, R.layout.page_explanation_03
        )
        val circles = listOf(binding.circleLeft, binding.circleMiddle, binding.circleRight)
        var activeCircle = binding.circleLeft
        (activeCircle.background as TransitionDrawable).startTransition(0)

        val pagerAdapter = ExplanationPagerAdapter(requireContext(), pages)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                (circles[position].background as TransitionDrawable).startTransition(200)
                (activeCircle.background as TransitionDrawable).reverseTransition(200)
                activeCircle = circles[position]
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                // no-op
            }
            override fun onPageScrollStateChanged(state: Int) {
                // no-op
            }
        })

        return binding.root
    }
}
