package com.fdev.yourdrive.ui.screen.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fdev.yourdrive.MainViewModel
import com.fdev.yourdrive.domain.model.FileData
import com.fdev.yourdrive.ui.theme.YourDriveTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val imageList = viewModel._images.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val images = remember {
        mutableStateOf<List<FileData>>(emptyList())
    }

    LaunchedEffect(imageList){
        scope.launch {
        delay(10000)
            images.value = imageList.value.toList()
        }
    }

    YourDriveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (images.value.isNotEmpty()) {
                    LazyVerticalStaggeredGrid(
                        columns =  StaggeredGridCells.Adaptive(100.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalItemSpacing = 16.dp
                    ){
                        items(imageList.value){
                            Image(
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(2.dp)),
                                bitmap = it.source.asImageBitmap(), contentDescription = "Image from Samba share"
                            )
                        }
                    }
                }
                else {
                    Text(text = "Loading...")
                }
            }
        }
    }
}