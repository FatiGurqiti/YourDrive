package com.fdev.yourdrive.ui.screen.main

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.common.manager.backupManager.BackupManager
import com.fdev.yourdrive.domain.model.NetworkAuth
import com.fdev.yourdrive.ui.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val backupManager: BackupManager
) : BaseViewModel<MainState, MainEvent, MainEffect>() {

    override val initialState: MainState
        get() = MainState()

    override fun onEvent(event: MainEvent) {
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val networkAuth = NetworkAuth("fati","4105","smb://fati/sambashare/mytestfolder/")
            backupManager.backup(networkAuth)
        }
    }
}