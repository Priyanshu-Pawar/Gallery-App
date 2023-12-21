package com.demo.galleryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val list: MutableList<Fragment> = ArrayList()
    override fun getCount(): Int {
        return list.size
    }

    fun addFragment(fragment: Fragment) {
        list.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (position == 0) {
            "All Images"
        } else {
            "Folder"
        }
    }
}