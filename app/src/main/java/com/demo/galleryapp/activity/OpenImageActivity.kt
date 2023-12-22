package com.demo.galleryapp.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.demo.galleryapp.R
import com.demo.galleryapp.adapters.ImageSliderAdapter
import com.demo.galleryapp.adapters.ImagesAdapter

class OpenImageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager
    private lateinit var textView: TextView
    private lateinit var backBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_image)

        viewPager = findViewById(R.id.viewPager_slider)
        recyclerView = findViewById(R.id.recycler_view)
        textView = findViewById(R.id.open_text_view)
        backBtn = findViewById(R.id.back_btn)

        backBtn.setOnClickListener{
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        // this code if for getting the images name and show in activity
        if(intent.hasExtra("imageName")){
            val imageName = intent.getStringExtra("imageName")
            textView.text = imageName
        }

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