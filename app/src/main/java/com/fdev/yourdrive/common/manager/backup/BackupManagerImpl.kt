package com.fdev.yourdrive.common.manager.backup

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.fdev.yourdrive.common.util.FlowUtil
import com.fdev.yourdrive.common.util.addSlashIfNeeded
import com.fdev.yourdrive.common.util.isSupportedFile
import com.fdev.yourdrive.domain.enumeration.Result
import com.fdev.yourdrive.domain.manager.BackupManager
import com.fdev.yourdrive.domain.manager.CrashlyticsManager
import com.fdev.yourdrive.domain.model.MediaItem
import com.fdev.yourdrive.domain.model.NetworkAuth
import jcifs.CIFSContext
import jcifs.config.PropertyConfiguration
import jcifs.context.BaseContext
import jcifs.smb.NtlmPasswordAuthenticator
import jcifs.smb.SmbAuthException
import jcifs.smb.SmbFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.time.LocalDateTime

class BackupManagerImpl(
    private val context: Context,
    private val crashlyticsManager: CrashlyticsManager
) : BackupManager {

    companion object {
        private const val FILE_NAME = "YourDrive"
    }

    private lateinit var networkAuth: NetworkAuth
    private lateinit var baseCxt: CIFSContext
    private lateinit var auth: NtlmPasswordAuthenticator
    private lateinit var cifsContext: CIFSContext
    private lateinit var smbFile: SmbFile

    override suspend fun connect(networkAuth: NetworkAuth): Result {
        return try {
            withContext(Dispatchers.IO) {
                this@BackupManagerImpl.baseCxt =
                    BaseContext(PropertyConfiguration(System.getProperties()))
                this@BackupManagerImpl.auth =
                    NtlmPasswordAuthenticator(networkAuth.username, networkAuth.password)
                this@BackupManagerImpl.cifsContext = baseCxt.withCredentials(auth)
                val smbFile = SmbFile(networkAuth.remoteURL, cifsContext)

                createFileIfNeeded(networkAuth.remoteURL, smbFile, cifsContext)
                this@BackupManagerImpl.networkAuth = networkAuth
                Result.SUCCESS
            }
        } catch (e: SmbAuthException) {
            crashlyticsManager.logNonFatalException(e)
            Result.FAILED(e.message.toString())
        } catch (e: Exception) {
            crashlyticsManager.logNonFatalException(e)
            Result.FAILED(e.message.toString())
        }
    }

    private fun createFileIfNeeded(url: String, smbFile: SmbFile, ct: CIFSContext) {
        if (url.contains(FILE_NAME)) {  // Already in the correct folder
            smbFile.connect()
            this.smbFile = smbFile
            return
        }

        var newURL = "${url}${FILE_NAME}"
        val isChild = smbFile.listFiles().map { it.name }.contains(FILE_NAME)

        if (isChild) {
            newURL += FILE_NAME
        }

        val newFolder = SmbFile(newURL, ct)
        if (!newFolder.exists()) newFolder.mkdir()
        newFolder.connect()
        this.smbFile = newFolder
    }

    override suspend fun backup() = channelFlow {
        withContext(Dispatchers.IO) {
            val progressContext = CoroutineScope(SupervisorJob() + Dispatchers.Default)
            val flowUtil = FlowUtil<List<String>>()

            flowUtil.onErrorEmptyOrCompletion(
                request = getRemoteImages(),
                action = { progressContext.cancel() }
            ).collect { remoteImages ->
                getLocalImages(context).collect { localImages ->
                    iterateFiles(localImages, remoteImages) {
                        progressContext.launch {
                            send(it)
                        }
                    }
                }
            }
        }
    }

    private fun getRemoteImages() = flow {
        try {
            val images = smbFile.listFiles().filter { it.name.isSupportedFile() }
            val imagesFile = images.map { it.name.replace(FILE_NAME, "") }

            emit(imagesFile)
        } catch (e: Exception) {
            crashlyticsManager.logNonFatalException(e)
        } finally {
            emit(emptyList())
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
        onNewItemAdded: (Float) -> Unit
    ) {
        val images =
            localImages.filter { it.name !in remoteImages } // Don't add the already added images

        images.forEachIndexed { index, it ->
            addToNetworkDrive(
                it.name ?: LocalDateTime.now().toString(),
                it.path
            )

            val progress = index.toFloat() / images.size.toFloat() * 100
            onNewItemAdded(progress)
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

    private fun addToNetworkDrive(fileName: String, filePath: String) {
        val remoteURL = "${networkAuth.remoteURL.cdIfInParentFolder()}$fileName"
        val destinationFile = SmbFile(remoteURL, cifsContext)

        try {
            if (!destinationFile.exists()) {
                destinationFile.createNewFile()
            }

            writeToNetworkDrive(filePath, destinationFile)
        } catch (e: Exception) {
            crashlyticsManager.logNonFatalException(e)
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

    private fun String.cdIfInParentFolder(): String {
        return if (!contains(FILE_NAME)) {
            "${this.addSlashIfNeeded()}$FILE_NAME/"
        } else {
            this
        }
    }
}