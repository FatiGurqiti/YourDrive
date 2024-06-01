package com.fdev.yourdrive

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _myState = MutableStateFlow<ImageBitmap?>(null)
    val myState: StateFlow<ImageBitmap?> = _myState.asStateFlow()

    val _images = MutableStateFlow<MutableList<FileData>>(mutableListOf())
    private val local_images = mutableListOf<FileData>()
    val images: StateFlow<MutableList<FileData>> = _images.asStateFlow()

    init {
        getImages()
//        loadImageFromSamba("design.webp")
    }

    fun getImages() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val userName = "fati"
                val password = "4105"
                val remoteURL = "smb://fati/sambashare/mytestfolder"

                val baseCxt: CIFSContext = BaseContext(PropertyConfiguration(System.getProperties()))
                val auth = NtlmPasswordAuthenticator(userName, password)
                val ct = baseCxt.withCredentials(auth)
                val smbFile = SmbFile(remoteURL, ct)

                val files = mutableListOf<String>()

                for (file in smbFile.listFiles()) {
                    val stringFile = file.toString()
                    if (!stringFile.contains("._") && stringFile.isImage()) {
                        val rawFileName = file.name
                        val fileName = rawFileName.replace("mytestfolder", "")
                        println("hajde here: ${fileName}")
                        files.add(file.name)

//                        val inputStream = SmbFileInputStream(smbFile)

//                         val imageBytes = inputStream.readBytes()
//                        inputStream.close()
//                        _myState.value = (BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)).asImageBitmap()

                        println("hajdeO0: $fileName")
                        loadImageFromSamba(fileName)
//                        _images.value.add(fileName)
                    }
                }

                _images.value = local_images

                println("hajde: $files")
            }
        } catch (e: Exception) {
            println("hajde: ${e}")
            // Handle connection or authentication errors

        }
    }

    fun loadImageFromSamba(file: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val userName = "fati"
                val password = "4105"
                val remoteFile = "/mytestfolder/$file"
                val remoteURL = "smb://fati/sambashare${remoteFile}"

                val baseCxt: CIFSContext = BaseContext(PropertyConfiguration(System.getProperties()))
                val auth = NtlmPasswordAuthenticator(userName, password)
                val ct = baseCxt.withCredentials(auth)
                val smbFile = SmbFile(remoteURL, ct)

                if (smbFile.isFile || true) {
                    val inputStream = SmbFileInputStream(smbFile)
                    val imageBytes = inputStream.readBytes()
                    inputStream.close()
                    val bitmap = (BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size))

                    println("hajdeO1: ${file}")
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
            }
        } catch (e: Exception) {
            println("hajde: ${e}")
            // Handle connection or authentication errors

        }
    }

}