package com.demo.galleryapp.models
class MdAll(
    var id: Long,
    var bucketId: Long,
    var path: String,
    var miliSecond: Long,
    var duration: Long,
    val isPhoto: Boolean,
    val date: String
) {
    constructor(other: MdAll) : this(
        other.id,
        other.bucketId,
        other.path,
        other.miliSecond,
        other.duration,
        other.isPhoto,
        other.date
    )
}