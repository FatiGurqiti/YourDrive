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