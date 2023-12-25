package com.demo.galleryapp.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.demo.galleryapp.AppClass
import com.demo.galleryapp.MainViewModel
import com.demo.galleryapp.R
import com.demo.galleryapp.adapters.ViewPagerAdapter
import com.demo.galleryapp.fragments.FolderFragment
import com.demo.galleryapp.fragments.GalleryFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var galleryFragment: GalleryFragment
    private lateinit var folderFragment: FolderFragment
    private lateinit var progressBar: ProgressBar
    private lateinit var service: ExecutorService
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var openMenuNav: CardView

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            (application as AppClass).mainViewModel = MainViewModel(application)
            loadData()
        } else {
            Toast.makeText(this, "Permission Required!!!", Toast.LENGTH_SHORT).show()
//            askForPermission()
            showRationaleOrOpenSettings()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayoutView)
        viewPager = findViewById(R.id.view_pager)
        progressBar = findViewById(R.id.progressBar)
        openMenuNav = findViewById(R.id.openMenu)

        drawerLayout = findViewById(R.id.drawerLayoutView)
        toolbar = findViewById(R.id.toolBar)
        navigationView = findViewById(R.id.nav_view)

        service = Executors.newSingleThreadExecutor()

        progressBar.visibility = View.VISIBLE

        if (permissionCheck()) {
            service.execute {
                loadData()
            }
        } else {
            askForPermission()
        }

        // setting the drawerLayout in main screen
//        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer
        )


        openMenuNav.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.addDrawerListener(toggle)

        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    private fun loadData() {

        (application as AppClass).mainViewModel = MainViewModel(application)

        galleryFragment = GalleryFragment.newInstance(0)
        folderFragment = FolderFragment.newInstance(1)

        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(galleryFragment)
        adapter.addFragment(folderFragment)

        runOnUiThread {
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
            progressBar.visibility = View.GONE
        }
    }

    private fun askForPermission() {

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
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
        ActivityCompat.requestPermissions(this, permission, 100)

//        requestPermissionLauncher.launch(permission)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}