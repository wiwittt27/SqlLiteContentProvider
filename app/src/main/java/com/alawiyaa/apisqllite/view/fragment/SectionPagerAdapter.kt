package com.alawiyaa.apisqllite.view.fragment

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.alawiyaa.apisqllite.R


class SectionPagerAdapter(private val ctx: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_follower, R.string.tab_following)


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1-> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }


    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return ctx.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int {
        return 2
    }

}