package com.fdev.yourdrive

import android.graphics.BitmapFactory
import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.enum.FileType
import com.fdev.yourdrive.domain.model.FileData
import com.fdev.yourdrive.common.util.isImage
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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainState, MainEvent, MainEffect>() {
    init {
        getImages()
    }

    override val initialState: MainState
        get() = MainState()

    override fun onEvent(event: MainEvent) {
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

                        println("hajde: ${fileName}")
                        loadImageFromSamba(fileName, ct)
                    }
                }
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