package com.fdev.yourdrive.common.util

val String.Companion.Empty: String get() = ""

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

    val lastSlashIndex = this.lastIndexOf("/") + 1
    return this.substring(lastSlashIndex)
}

fun String.isSupportedFile(): Boolean {
    return !contains("._") && isImage()
}

fun String.setNullIfEmpty() = this.ifEmpty { null }

fun String.addSlashIfNeeded(): String {
    return if (last() == '/') this
    else "$this/"
}
