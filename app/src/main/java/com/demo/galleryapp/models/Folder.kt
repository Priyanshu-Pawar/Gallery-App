package com.demo.galleryapp.models

import java.io.Serializable

data class Folder(
    val name: String,  val models: ArrayList<Model>
): Serializable