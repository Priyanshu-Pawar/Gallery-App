package com.demo.galleryapp

import android.app.Application
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.galleryapp.models.Model

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _gData: MutableLiveData<List<Model>> = MutableLiveData()
    val gData: LiveData<List<Model>> get() = _gData

    init {
        loadImages()
    }

    private fun loadImages() {
        val context: Context = getApplication()
        val tempList = ArrayList<Model>()
        //         val uri = when {
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
//                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
//              MediaStore.Files.getContentUri("external")
//            }
//
//            else -> {
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            }
//        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null
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

        _gData.postValue(tempList)
    }


}