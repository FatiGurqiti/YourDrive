package com.fdev.yourdrive.common.manager.backupManager

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.fdev.yourdrive.common.util.isSupportedFile
import com.fdev.yourdrive.domain.model.MediaItem
import com.fdev.yourdrive.domain.model.NetworkAuth
import jcifs.CIFSContext
import jcifs.config.PropertyConfiguration
import jcifs.context.BaseContext
import jcifs.smb.NtlmPasswordAuthenticator
import jcifs.smb.SmbFile
import kotlinx.coroutines.flow.flow
import java.io.FileInputStream
import java.time.LocalDateTime

class BackupManagerImpl(private val context: Context) : BackupManager {

    companion object {
        private const val FILE_NAME = "YourDrive"
    }

    override suspend fun backup(networkAuth: NetworkAuth) {
        getRemoteImages(networkAuth).collect { remoteImages ->
            getLocalImages(context).collect { localImages ->
                iterateFiles(localImages, remoteImages, networkAuth)
            }
        } 
    }

    private fun getRemoteImages(networkAuth: NetworkAuth) = flow {
        try {
            val baseCxt: CIFSContext = BaseContext(PropertyConfiguration(System.getProperties()))
            val auth = NtlmPasswordAuthenticator(networkAuth.userName, networkAuth.password)
            val ct = baseCxt.withCredentials(auth)
            val smbFile = SmbFile(networkAuth.remoteURL, ct)

            val images = smbFile.listFiles().filter { it.name.isSupportedFile() }
            val imagesFile = images.map { it.name.replace("mytestfolder", "") }

            emit(imagesFile)
        } catch (e: Exception) {
            println("hajde: $e")
            //TODO("Send error to firebase")
            //TODO("Show error in UI")
        }
    }

    private fun getLocalImages(context: Context) = flow {
        val contentResolver = context.contentResolver

        // Query for Images
        val imageCursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media.DATA, MediaStore.MediaColumns.MIME_TYPE),
            null,
            null,
            null
        )

        emit(imageQuery(imageCursor))
    }

    private fun iterateFiles(
        localImages: List<MediaItem>,
        remoteImages: List<String>,
        networkAuth: NetworkAuth
    ) {
        val images =
            localImages.filter { it.name !in remoteImages } // Don't add the already added images

        images.forEach {
            addToNetworkDrive(
                networkAuth,
                it.name ?: LocalDateTime.now().toString(),
                it.path
            )
        }
    }

    private fun imageQuery(cursor: Cursor?): List<MediaItem> {
        val mediaList = mutableListOf<MediaItem>()

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val imageIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val imageMimeTypeIndex =
                    cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)

                if (imageIndex < 0 || imageMimeTypeIndex < 0) break

                val path = cursor.getString(imageIndex)
                val mimeType = cursor.getString(imageMimeTypeIndex)
                mediaList.add(MediaItem(path, mimeType))
            }
            cursor.close()
        }

        return mediaList.toList()
    }

    private fun addToNetworkDrive(networkAuth: NetworkAuth, fileName: String, filePath: String) {
        val remoteURL = "${networkAuth.remoteURL}/$fileName"

        val baseCxt: CIFSContext = BaseContext(PropertyConfiguration(System.getProperties()))
        val auth = NtlmPasswordAuthenticator(networkAuth.userName, networkAuth.password)
        val ct = baseCxt.withCredentials(auth)
        val destinationFile = SmbFile(remoteURL, ct)

        try {
            if (!destinationFile.exists()) {
                destinationFile.createNewFile()
            }

            writeToNetworkDrive(filePath, destinationFile)
        } catch (e: Exception) {
            //TODO("Send error to firebase")
            println("hajde Error: ${e.message}")
        }
    }

    private fun writeToNetworkDrive(filePath: String, file: SmbFile) {
        val inputStream = FileInputStream(filePath)
        val outputStream = file.outputStream

        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } > 0) {
            outputStream.write(buffer, 0, bytesRead)
        }

        inputStream.close()
        outputStream.close()
    }
}