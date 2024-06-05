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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fdev.yourdrive.ui.theme.YourDriveTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val state: MainState by viewModel.state.collectAsStateWithLifecycle()

    YourDriveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(contentAlignment = Alignment.Center) {
//                if (state.album.isNotEmpty()) {
                    LazyVerticalStaggeredGrid(
                        columns =  StaggeredGridCells.Adaptive(100.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalItemSpacing = 16.dp
                    ){
                        items(state.album) {
                            Image(
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(2.dp)),
                                bitmap = it.source.asImageBitmap(), contentDescription = "Image from Samba share"
                            )
                        }
                    }
//                }
//                else {
//                    Text(text = "Loading...")
//                }
            }
        }
    }
}