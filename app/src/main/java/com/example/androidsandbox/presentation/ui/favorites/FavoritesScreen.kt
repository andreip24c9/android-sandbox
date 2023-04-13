package com.example.androidsandbox.presentation.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidsandbox.presentation.ui.common.SandboxCompose
import com.example.androidsandbox.presentation.ui.common.widgets.SandboxToolbar
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@Composable
fun FavoritesScreenRoute(modifier: Modifier = Modifier) {
    FavoritesScreen()
}
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier,
        topBar = {
            SandboxToolbar(title = "Favorites")
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Second tab screen")
        }
    }
}

@SandboxCompose.LightDarkModePreview
@Composable
fun PreviewFavoritesScreen() {
    AndroidSandboxTheme {
        FavoritesScreen(modifier = Modifier.fillMaxSize())
    }
}