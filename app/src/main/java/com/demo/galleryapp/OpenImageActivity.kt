package com.demo.galleryapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.demo.galleryapp.adapters.ImageSliderAdapter
import com.demo.galleryapp.adapters.ImagesAdapter
import com.demo.galleryapp.models.Model

class OpenImageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_image)

        viewPager = findViewById(R.id.viewPager_slider)
        recyclerView = findViewById(R.id.recycler_view)

        if (intent.hasExtra("folderList")) {
            val newList = intent.extras?.get("folderList") as? ArrayList<Model>

            if (!newList.isNullOrEmpty()) {
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager =
                    GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = ImagesAdapter(this, newList)

            }

        } else {

            val models = ImagesAdapter.MyViewHolder.DataHolder.modelList
            if (!models.isNullOrEmpty()) {
                val selectedPosition = intent.getIntExtra("selectedImagePosition", 0)
                val adapter = ImageSliderAdapter(this, models)
                recyclerView.visibility = View.GONE
                viewPager.adapter = adapter
                viewPager.setCurrentItem(selectedPosition, false)
            }

        }
    }
}