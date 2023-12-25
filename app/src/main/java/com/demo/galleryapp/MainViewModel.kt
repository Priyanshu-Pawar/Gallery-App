package com.demo.galleryapp

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.galleryapp.models.Folder
import com.demo.galleryapp.models.Model
import java.io.File
import kotlin.coroutines.coroutineContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _gData: MutableLiveData<List<Model>> = MutableLiveData()
    val gData: LiveData<List<Model>> get() = _gData
    var allImagesList: ArrayList<Model> = loadImages()
    val folderList: ArrayList<Folder> = ArrayList()
    private val updateImageList: ArrayList<Model> = ArrayList()


    init {
        allImagesList = loadImages()
    }

    private fun loadImages(): ArrayList<Model> {
        val context: Context = getApplication()
        val tempList = ArrayList<Model>()

        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
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
//                    val filePath =
//                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
//                    val filePath = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val filePath =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
//                      }
//                    else {
//                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.RELATIVE_PATH))
//                    }
                    val imageName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))


                    val model = Model(picId, filePath, imageName)
                    tempList.add(model)

                }
            }
        }

        _gData.postValue(tempList)
        return tempList
    }

    fun deleteImage(imagePathToDelete: String) {

//        val imagePath = File(imagePathToDelete)
//        val trashDirectory = createTrashDirectory()
//        imagePath.copyTo(trashDirectory)
//        val contentResolver: ContentResolver = getApplication<Application>().contentResolver
//
//        imagePath.delete()
//
//        // Optionally, move the image to the trash directory
//        moveImageToTrash(contentResolver, imagePathToDelete, trashDirectory)
//
//        // Update the LiveData list
//        updateImageList.removeAll { it.name == imagePathToDelete }
//        _gData.postValue(updateImageList)

        if (imagePathToDelete.isEmpty()) {
            // Optionally handle the case where imagePathToDelete is empty
            return
        }

        val imagePath = File(imagePathToDelete)
        val trashDirectory = createTrashDirectory()
        val contentResolver: ContentResolver = getApplication<Application>().contentResolver

        // Optionally, move the image to the trash directory before deleting
        moveImageToTrash(contentResolver, imagePathToDelete, trashDirectory)

        // Check if the file exists before attempting to delete
        if (imagePath.exists()) {
            // Delete the file
            imagePath.delete()
            // Update the LiveData list
            updateImageList.removeAll { it.name == imagePathToDelete }
            _gData.postValue(updateImageList)
        } else {

        }
    }

    private fun moveImageToTrash(
        contentResolver: ContentResolver, imagePath: String, trashDirectory: File
    ) {
        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = "${MediaStore.Images.Media.DATA} = ?"
        val selectionArgs = arrayOf(imagePath)

        val contentValues = ContentValues()
        contentValues.put(
            MediaStore.Images.Media.DATA, File(trashDirectory, File(imagePath).name).path
        )

        val updatedRows = contentResolver.update(imageUri, contentValues, selection, selectionArgs)

        if (updatedRows > 0) {
//            Toast.makeText(coroutineContext, "Image moved to trash successfully", Toast.LENGTH_SHORT).show()
            Toast.makeText(getApplication(), "Image moved to trash successfully", Toast.LENGTH_SHORT).show()
        } else {

//            Log.d("ImageMoveToTrash", "Failed to move image to trash")
            Toast.makeText(getApplication(), "Failed to move image to trash", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createTrashDirectory(): File {
        val trashDirectory = File(Environment.getExternalStorageDirectory(), "TrashBin")
        if (!trashDirectory.exists()) {
            trashDirectory.mkdirs()
        }
        return trashDirectory
    }



//    fun moveImageToTrash(contentResolver: ContentResolver, imagePath: String) {
//        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val selection = "${MediaStore.Images.Media.DATA} = ?"
//        val selectionArgs = arrayOf(imagePath)
//
//        val contentValues = ContentValues()
//        contentValues.put(
//            MediaStore.Images.Media.DATA, File(createTrashDirectory(), File(imagePath).name).path
//        )
//
//        val updatedRows = contentResolver.update(imageUri, contentValues, selection, selectionArgs)
//
//        if (updatedRows > 0) {
//            Log.d("ImageMoveToTrash", "Image moved to trash successfully")
//        } else {
//            Log.d("ImageMoveToTrash", "Failed to move image to trash")
//        }
//    }


//    private fun createTrashDirectory(): File {
//        val trashDirectory = File(Environment.getExternalStorageDirectory(), "TrashBin")
//        if (!trashDirectory.exists()) {
//            trashDirectory.mkdirs()
//        }
//        return trashDirectory
//    }

//    fun Context.deleteImages(myPath :String)
//    {
//
//        val sourec= File(myPath)
//        val dest= File("new path/ trash/",File(myPath).name)
//        sourec.copyTo(dest)
//        sourec.deleteRecursively()
//
//        val mainList111 = ArrayList<MdAll>()
//        mainList111.addAll(gData.value!!.toMutableList())
//        mainList111.removeAll { it.path== myPath}
//        gData.postValue(mainList111)
//    }

}
