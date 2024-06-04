package com.fdev.yourdrive

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.common.util.isImage
import com.fdev.yourdrive.common.util.pathToName
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainState, MainEvent, MainEffect>() {

    override val initialState: MainState
        get() = MainState()

    override fun onEvent(event: MainEvent) {
    }

    fun setup(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            getRemoteImages().collect { remoteImages ->
                getLocalImages(context)
                    .collect { localImages ->
                        val images = localImages.filter { it.name !in remoteImages }

                        images.forEach {
                            addToNetworkDrive(it.name ?: LocalDateTime.now().toString(), it.path)
                        }
                    }
            }
        }
    }

    private fun getLocalImages(context: Context) = flow<List<MediaItem>> {
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
                val imageMimeTypeIndex =
                    imageCursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)

                if (imageIndex < 0 || imageMimeTypeIndex < 0) break

                val path = imageCursor.getString(imageIndex)
                val mimeType = imageCursor.getString(imageMimeTypeIndex)
                mediaList.add(MediaItem(path, mimeType))
            }
            imageCursor.close()
        }
        emit(mediaList)
    }

    data class MediaItem(val path: String, val mimeType: String?) {
        val name = path.pathToName()
    }

    private fun addToNetworkDrive(fileName: String, filePath: String) {
        println("hajde started")
        val userName = "fati"
        val password = "fati"
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

    private fun getRemoteImages() = flow {
        try {
            val userName = "fati"
            val password = "fati"
            val remoteURL = "smb://fati/sambashare/mytestfolder"

            val baseCxt: CIFSContext = BaseContext(PropertyConfiguration(System.getProperties()))
            val auth = NtlmPasswordAuthenticator(userName, password)
            val ct = baseCxt.withCredentials(auth)
            val smbFile = SmbFile(remoteURL, ct)

            val images = smbFile.listFiles().filter {
                !it.name.contains("._") && it.name.isImage()
            }

            val imagesFile = images.map { it.name.replace("mytestfolder", "") }
            emit(imagesFile)
        } catch (e: Exception) {
            println("hajde: ${e}")
            // Handle connection or authentication errors
        }
    }
}