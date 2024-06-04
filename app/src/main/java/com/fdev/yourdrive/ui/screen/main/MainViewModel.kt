package com.fdev.yourdrive

import android.content.Context
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.enum.FileType
import com.fdev.yourdrive.domain.model.FileData
import com.fdev.yourdrive.ui.screen.base.BaseViewModel
import com.fdev.yourdrive.ui.screen.main.MainEffect
import com.fdev.yourdrive.ui.screen.main.MainEvent
import com.fdev.yourdrive.ui.screen.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import jcifs.CIFSContext
import jcifs.config.PropertyConfiguration
import jcifs.context.BaseContext
import jcifs.smb.NtlmPasswordAuthenticator
import jcifs.smb.SmbFile
import jcifs.smb.SmbFileInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainState, MainEvent, MainEffect>() {
    init {
//        getImages()
    }

    override val initialState: MainState
        get() = MainState()

    override fun onEvent(event: MainEvent) {
    }

    fun setup(context: Context){

//        getAllMedia(context).forEach {
//            println("hajde: ${it.path}")
//            val file = File(it.path)
//
//
//            return@forEach
//        }

        val it = getAllMedia(context).filter {
            it.path.endsWith(".png")
        }


        viewModelScope.launch(Dispatchers.IO) {
            it.forEach {
            val file = File(it.path)
            addToNetworkDrive(file.name, it.path)
            }
        }
    }


    fun getAllMedia(context: Context): List<MediaItem> {
        val mediaList = mutableListOf<MediaItem>()

        val contentResolver = context.contentResolver

        // Query for Images
        val imageCursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media.DATA, MediaStore.MediaColumns.MIME_TYPE),
            null,
            null,
            null
        )

        if (imageCursor != null) {
            while (imageCursor.moveToNext()) {
                val imageIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val imageMimeTypeIndex = imageCursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)

                if (imageIndex < 0 || imageMimeTypeIndex < 0) break

                val path = imageCursor.getString(imageIndex)
                val mimeType = imageCursor.getString(imageMimeTypeIndex)
                mediaList.add(MediaItem(path, mimeType))
            }
            imageCursor.close()
        }

        // Query for Videos (optional filtering can be added here)
        val videoCursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Video.Media.DATA, MediaStore.MediaColumns.MIME_TYPE),
            null,
            null,
            null
        )

        if (videoCursor != null && false) {
            while (videoCursor.moveToNext()) {
                val videoIndex = videoCursor.getColumnIndex(MediaStore.Video.Media.DATA)
                val videoMimeTypeIndex = videoCursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)

                if (videoIndex < 0 || videoMimeTypeIndex < 0) break

                val path = videoCursor.getString(videoIndex)
                val mimeType = videoCursor.getString(videoMimeTypeIndex)
                mediaList.add(MediaItem(path, mimeType))
            }
            videoCursor.close()
        }

        return mediaList
    }

    // Define a data class to hold media information (path and mime type)
    data class MediaItem(val path: String, val mimeType: String?)

    fun addToNetworkDrive(fileName: String, filePath: String) {

            println("hajde started")
            val userName = "fati"
            val password = "4105"
            val remoteURL = "smb://fati/sambashare/mytestfolder/$fileName"

            val baseCxt: CIFSContext = BaseContext(PropertyConfiguration(System.getProperties()))
            val auth = NtlmPasswordAuthenticator(userName, password)
            val ct = baseCxt.withCredentials(auth)
            val destinationFile = SmbFile(remoteURL, ct)
            // Open input stream from local file

            try {
//                val destinationFile = SmbFile(remoteURL, ct)

                // Check if remote file already exists (optional)
                if (destinationFile.exists()) {
                    // Handle existing file (e.g., overwrite or throw an error)
                } else {
                    // Create a new empty file on the server
                    destinationFile.createNewFile()
                }

                val inputStream = FileInputStream(filePath)
                val outputStream = destinationFile.outputStream

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } > 0) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                inputStream.close()
                outputStream.close()

                println("hajde: success")
        } catch (e: Exception) {
            println("hajde Error: ${e.message}")
        }

    }


    private fun loadImageFromSamba(file: String, cifsContext: CIFSContext) { // Although this isn't a suspend fun, it still has to be called from async in order for Samba to work properly.
        try {
                val remoteFile = "/mytestfolder/$file"
                val remoteURL = "smb://fati/sambashare${remoteFile}"
                val smbFile = SmbFile(remoteURL, cifsContext)

                if (smbFile.isFile) {
                    val inputStream = SmbFileInputStream(smbFile)
                    val imageBytes = inputStream.readBytes()
                    inputStream.close()
                    val bitmap = (BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size))

                    val fileType = FileData(
                        FileType.IMAGE,
                        file,
                        source = bitmap
                    )

                    val items = state.value.album.plus(fileType)
                    setState { copy(album = items) }
                } else {
                    // Handle error - not a file
                 }
        } catch (e: Exception) {
            // Handle error
        }
    }
}