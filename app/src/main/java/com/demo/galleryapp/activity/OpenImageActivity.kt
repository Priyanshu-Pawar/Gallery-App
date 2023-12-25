package com.demo.galleryapp.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.demo.galleryapp.AppClass
import com.demo.galleryapp.MainViewModel
import com.demo.galleryapp.MainViewModelFactory
import com.demo.galleryapp.R
import com.demo.galleryapp.adapters.ImageSliderAdapter
import com.demo.galleryapp.models.Model
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File

class OpenImageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager
    private lateinit var textView: TextView
    private lateinit var backBtn: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var imagesAdapter: ImageSliderAdapter
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_image)

        val models = if (intent.getBooleanExtra("isAllData", true)) {
            (application as AppClass).mainViewModel.allImagesList
        } else {
            (application as AppClass).mainViewModel.folderList[intent.getIntExtra(
                "folderPosition",
                0
            )].models
        }

        viewPager = findViewById(R.id.viewPager_slider)
        recyclerView = findViewById(R.id.recycler_view)
        textView = findViewById(R.id.open_text_view)
        backBtn = findViewById(R.id.back_btn)
        bottomNavigationView = findViewById(R.id.bottomNavigation)

        toolbar = findViewById(R.id.toolBar)




        backBtn.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        if (models.isNotEmpty()) {
            val selectedPosition = intent.getIntExtra("selectedImagePosition", 0)
            imagesAdapter = ImageSliderAdapter(this, models)
            recyclerView.visibility = View.GONE
            viewPager.adapter = imagesAdapter
            viewPager.setCurrentItem(selectedPosition, false)
        }

        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                textView.text = models[position].imageName
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })


//        mainViewModel.gData.observe(this) { updatedData ->
//            if (updatedData.isNotEmpty()) {
//                val selectedPosition = viewPager.currentItem.coerceIn(0, updatedData.size - 1)
//                val adapter = ImageSliderAdapter(this, updatedData as ArrayList<Model>)
//                viewPager.adapter = adapter
//                viewPager.setCurrentItem(selectedPosition, false)
//            } else {
//                // Handle the case when there are no images after deletion
//            }
//        }


        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val id: Int = menuItem.itemId

            when (id) {
                R.id.shareItem -> {
                    // Handle share item
                }

                R.id.deleteItem -> {

//                    val pos = viewPager.currentItem
//
//                    val mod: Model = models.elementAt(pos)
//                    if(File(mod.name).delete()){
//                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
//                    }
//
//                    imagesAdapter.notifyDataSetChanged()

                    val imageDelete = viewPager.currentItem
//                    mainViewModel.deleteImage(imageDelete.toString())
                    AlertDialog.Builder(this)
                        .setTitle("Delete Image")
                        .setMessage("Are you sure you want to delete this image?")
                        .setPositiveButton("Yes") { _, _ ->
                            // User clicked "Yes", proceed with deletion
                            (application as AppClass).mainViewModel.deleteImage(imageDelete.toString())
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            // User clicked "No", do nothing
                            dialog.dismiss()
                        }
                        .show()
                }

                R.id.hideItem -> {
                    // Handle hide item
                }

                else -> {
                    // Handle other items
                }
            }

            imagesAdapter.notifyDataSetChanged()
            true // Return true to indicate that the item selection is handled
        }


        // this code handling the toolbar item
        toolbar.setOnMenuItemClickListener {menuItem->
            when (menuItem.itemId) {
                R.id.favorites_item_toolbar -> {
                    // Handle click for menu item 1
                    Toast.makeText(this, "Click favorites", Toast.LENGTH_SHORT).show()
                    true // Return true to consume the click event
                }
                R.id.three_dot_toolbar -> {
                    // Handle click for menu item 2
                    Toast.makeText(this, "Click three dot", Toast.LENGTH_SHORT).show()
                    true // Return true to consume the click event
                }
                // Add other menu item cases if needed
                else -> false // Return false for items you don't handle to propagate the event
            }
        }
    }
}