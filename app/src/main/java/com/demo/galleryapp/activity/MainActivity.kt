package com.demo.galleryapp.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.demo.galleryapp.R
import com.demo.galleryapp.adapters.ViewPagerAdapter
import com.demo.galleryapp.fragments.FolderFragment
import com.demo.galleryapp.fragments.GalleryFragment
import com.google.android.material.tabs.TabLayout
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var galleryFragment: GalleryFragment
    private lateinit var folderFragment: FolderFragment
    private lateinit var progressBar: ProgressBar
    private lateinit var service: ExecutorService


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                loadData()
                Toast.makeText(this, "Permission Granted!!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Required!!!", Toast.LENGTH_SHORT).show()
                showRationaleOrOpenSettings()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayoutView)
        viewPager = findViewById(R.id.view_pager)
        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = View.VISIBLE


        service = Executors.newSingleThreadExecutor()

        progressBar.visibility = View.VISIBLE
        service.execute {
            if (permissionCheck()) {
                loadData()
            } else {
                askForPermission()
            }
        }

    }


    private fun loadData() {

        progressBar.visibility = View.GONE

        galleryFragment = GalleryFragment.newInstance(0)
        folderFragment = FolderFragment.newInstance(1)

        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(galleryFragment)
        adapter.addFragment(folderFragment)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun askForPermission() {

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 22
//            )
//        } else {
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 22
//            )
//        }

        requestPermissionLauncher.launch(permission)
    }

    private fun showAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(
            "package", packageName, null
        )   // Create a Uri specifying the package details for the current app.

        intent.data =
            uri  // Set the Uri as data for the Intent. This specifies the details of the app whose settings should be shown.
        startActivity(intent)
        finish()
    }

    private fun showRationaleOrOpenSettings() {
        val rationaleDialog = AlertDialog.Builder(this).setTitle("Permission Required")
            .setMessage("This feature requires storage permission. Please grant the permission in settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                showAppSettings()
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                finish()
            }.create()

        rationaleDialog.show()
    }

    private fun permissionCheck(): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED)
        } else {
            return (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED)
        }
    }
}