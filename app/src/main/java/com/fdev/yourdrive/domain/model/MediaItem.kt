package com.fdev.yourdrive.domain.model

import com.fdev.yourdrive.common.util.pathToName

data class MediaItem(val path: String, val mimeType: String?) {
    val name = path.pathToName()
}
