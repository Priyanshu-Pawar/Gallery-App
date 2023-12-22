package com.demo.galleryapp.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.demo.galleryapp.R
import com.demo.galleryapp.models.Model
import com.github.chrisbanes.photoview.PhotoView

class ImageSliderAdapter(private val context: Context, private val modelList: ArrayList<Model>) :
    PagerAdapter() {

    private lateinit var imageViewForSlider: PhotoView
    private var currentPosition = 0


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        currentPosition = position
        val view = LayoutInflater.from(context).inflate(R.layout.activity_open_one_image, container, false)

        imageViewForSlider = view.findViewById(R.id.imageView)

//        imageViewForSlider.scaleType = ImageView.ScaleType.FIT_CENTER

        Glide.with(context).load(modelList[position].name).listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(imageViewForSlider)

        container.addView(view)
        return view

    }

    override fun getCount(): Int {
        return modelList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}

