package com.fdev.yourdrive.domain.model

import android.graphics.Bitmap
import com.fdev.yourdrive.domain.enumeration.FileType

data class FileData(
    val type: FileType,
    val name: String,
    val source: Bitmap
)
