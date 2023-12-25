package com.demo.galleryapp.adapters

import android.media.Image
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.demo.galleryapp.models.Model


class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val list: MutableList<Fragment> = ArrayList()
    private var images: List<Model> = arrayListOf()
    override fun getCount(): Int {
        return list.size
    }

    fun addFragment(fragment: Fragment) {
        list.add(fragment)
    }

    fun setData(list: List<Model>) {
        this.images = list
        notifyDataSetChanged()
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