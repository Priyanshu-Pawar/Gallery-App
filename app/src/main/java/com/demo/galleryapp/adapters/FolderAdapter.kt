package com.demo.galleryapp.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.galleryapp.activity.FolderImagesActivity
import com.demo.galleryapp.R
import com.demo.galleryapp.models.Folder
import java.io.File

class FolderAdapter(private val context: Activity, private val list: List<Folder>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.folder_items, parent, false)
        return FolderViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.textView.text = File(list[position].name).name

        holder.image.setOnClickListener {
            val intent = Intent(context, FolderImagesActivity::class.java)
            intent.putExtra("folderList", list[position].models)
            intent.putExtra("folderName", File(list[position].name).name)
            context.startActivity(intent)
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        if (list[position].models.isNotEmpty()) {
            Glide.with(context).load(list[position].models[0].name)
                .placeholder(R.drawable.folder).fitCenter().into(holder.image)
        } else {
            // Handle the case when the folder is empty
            holder.image.setImageResource(R.drawable.folder)
        }
    }


    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image_view)
        var textView: TextView = itemView.findViewById(R.id.textView)
    }
}