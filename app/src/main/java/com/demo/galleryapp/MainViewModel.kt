package com.demo.galleryapp

import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import com.demo.galleryapp.models.Model

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private fun loadImages(): ArrayList<Model> {
        val context: Context = getApplication()
        val tempList = ArrayList<Model>()

        val uri = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
//                MediaStore.Files.getContentUri("external")
            }

            else -> {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        context.contentResolver.query(
            uri, projection, null, null, null
        )?.use { cursor ->
            cursor.let {
                while (cursor.moveToNext()) {
                    val picId =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val filePath =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    val imageName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))


                    val model = Model(picId, filePath, imageName)
                    tempList.add(model)

                }
            }
        }

        return tempList
    }

    fun getImages(): ArrayList<Model> {
        return loadImages()
    }

}