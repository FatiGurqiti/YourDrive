package com.fdev.yourdrive.presentation.composable.logo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.util.Empty
import com.fdev.yourdrive.presentation.composable.shape.RoundedPolygonShape

@Composable
fun HexagonLogo() {
    val hexagon = remember {
        RoundedPolygon(
            6,
            rounding = CornerRounding(0.2f)
        )
    }
    val clip = remember(hexagon) {
        RoundedPolygonShape(polygon = hexagon)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = String.Empty,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .graphicsLayer {
                    this.shadowElevation = 6.dp.toPx()
                    this.shape = clip
                    this.clip = true
                    this.ambientShadowColor = Color.Black
                    this.spotShadowColor = Color.Black
                }
                .size(200.dp)
                .background(MaterialTheme.colorScheme.background)
            ,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Preview
@Composable
fun HexagonLogoPreview() {
    HexagonLogo()
}