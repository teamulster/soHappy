package de.hsaugsburg.teamulster.sohappy.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * ExplanationPagerAdapter serves as the required adapter for the ViewPager in ExplanationFragment.
 */
class ExplanationPagerAdapter(private val context: Context, val pages: List<Int>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val page = pages[position]
        val inflater = LayoutInflater.from(context)
        val viewGroup = inflater.inflate(page, container, false)
        container.addView(viewGroup)

        return viewGroup
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
        container.removeView(obj as View)

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj
    override fun getCount(): Int = pages.size
}
