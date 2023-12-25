package com.demo.galleryapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.demo.galleryapp.R
import com.demo.galleryapp.activity.MainActivity
import com.demo.galleryapp.activity.OpenImageActivity
import com.demo.galleryapp.models.Model
import java.io.File

class ImagesAdapter(
    private val context: Context,
    private var list: ArrayList<Model>,
    val pos: Int
) :
    RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.images_item, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        holder.textView.text = list[position].imageName

        holder.image.setOnClickListener {
            val intent = Intent(context, OpenImageActivity::class.java)
//            val image = list[position].name
//            intent.putExtra("folderPath", image)
//            val filePaths = list.map { it.name }
            if (pos >= 0) {
                intent.putExtra("folderPosition", pos)
            }
            intent.putExtra("selectedImagePosition", position)
            intent.putExtra("isAllData", context is MainActivity)
            context.startActivity(intent)
//            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        val image = list[position].name
        Glide.with(context).load(File(image))
            .placeholder(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .listener(object : RequestListener<Drawable?> {
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

            }).into(holder.image)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image_view)

        //        var textView: TextView = itemView.findViewById(R.id.textView)
    }

}