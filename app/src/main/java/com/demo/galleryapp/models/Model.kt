package com.demo.galleryapp.models

import java.io.Serializable

data class Model(val id: Long,
                 val name: String,
                 val imageName: String
): Serializable
