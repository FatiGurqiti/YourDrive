package com.fdev.yourdrive

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.enum.FileType
import com.fdev.yourdrive.domain.model.FileData
import com.fdev.yourdrive.util.isImage
import dagger.hilt.android.lifecycle.HiltViewModel
import jcifs.CIFSContext
import jcifs.config.PropertyConfiguration
import jcifs.context.BaseContext
import jcifs.smb.NtlmPasswordAuthenticator
import jcifs.smb.SmbFile
import jcifs.smb.SmbFileInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val _images = MutableStateFlow<MutableList<FileData>>(mutableListOf())
    private val local_images = mutableListOf<FileData>()

    init {
        getImages()
    }

    private fun getImages() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val userName = "fati"
                val password = "4105"
                val remoteURL = "smb://fati/sambashare/mytestfolder"

                val baseCxt: CIFSContext = BaseContext(PropertyConfiguration(System.getProperties()))
                val auth = NtlmPasswordAuthenticator(userName, password)
                val ct = baseCxt.withCredentials(auth)
                val smbFile = SmbFile(remoteURL, ct)

                for (file in smbFile.listFiles()) {
                    val stringFile = file.toString()
                    if (!stringFile.contains("._") && stringFile.isImage()) {
                        val rawFileName = file.name
                        val fileName = rawFileName.replace("mytestfolder", "")
                        loadImageFromSamba(fileName, ct)
                    }
                }

                _images.value = local_images
            }
        } catch (e: Exception) {
            println("hajde: ${e}")
            // Handle connection or authentication errors
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

                    _images.value.add(fileType)
                    local_images.add(fileType)
                } else {
                    // Handle error - not a file
                 }
        } catch (e: Exception) {
            // Handle error
        }
    }
}