package com.demo.galleryapp.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.demo.galleryapp.R
import com.demo.galleryapp.adapters.ImagesAdapter
import com.demo.galleryapp.models.Model

class FolderImagesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager
    private lateinit var backBtn: ImageView
    private lateinit var setTextAccToData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_images)


        viewPager = findViewById(R.id.viewPager_slider)
        recyclerView = findViewById(R.id.recycler_view)
        backBtn = findViewById(R.id.back_btn)
        setTextAccToData = findViewById(R.id.open_text_view)

        backBtn.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        if (intent.hasExtra("folderName")) {
            val name = intent.getStringExtra("folderName")
            setTextAccToData.text = name
        }
        // IF USER SLIDE THE SCREEN ON FOLDER THEN THE BELOW CODE GIVES THE FOLDER LIST OF IMAGES
        if (intent.hasExtra("folderList")) {
            val newList = intent.extras?.get("folderList") as? ArrayList<Model>
            if (!newList.isNullOrEmpty()) {

                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager =
                    GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = ImagesAdapter(this, newList)
            }

        }
    }
}