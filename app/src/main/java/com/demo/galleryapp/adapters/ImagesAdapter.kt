package com.demo.galleryapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.demo.galleryapp.OpenImageActivity
import com.demo.galleryapp.R
import com.demo.galleryapp.models.Model
import java.io.File

class ImagesAdapter(private val context: Context, private val list: ArrayList<Model>) :
    RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.pic_item, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.textView.text = list[position].imageName

        holder.image.setOnClickListener {
            val intent = Intent(context, OpenImageActivity::class.java)
//            val image = list[position].name
//            intent.putExtra("folderPath", image)
//            val filePaths = list.map { it.name }
            MyViewHolder.DataHolder.modelList = ArrayList(list)
            intent.putExtra("selectedImagePosition", position)
//            val models = list.toList()
//            intent.putExtra("filePaths", ArrayList(models))
            context.startActivity(intent)
        }
        val image = list[position].name
        Glide.with(context).load(File(image)).listener(object : RequestListener<Drawable?> {
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

        }).placeholder(
            R.drawable.ic_launcher_background
        ).into(holder.image)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image_view)
        var textView: TextView = itemView.findViewById(R.id.textView)
        object DataHolder {
            var modelList: ArrayList<Model>? = null
        }
    }

}