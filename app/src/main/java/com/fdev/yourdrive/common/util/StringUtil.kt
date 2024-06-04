package com.fdev.yourdrive.common.util

fun String.isImage(): Boolean {
    val acceptedImageTypes = listOf(".png", ".jpg", ".jpeg", ".webp", ".bmp", ".gif")
    var value = false

    for (image in acceptedImageTypes) {
        if (this.endsWith(image)) {
            value = true
            break
        }
    }

    return value
}

fun String.pathToName(): String? {
    if (!this.contains("/")) return null

    val lastSlashIndex = this.lastIndexOf("/") +1
    return this.substring(lastSlashIndex)
}