package com.example.androidsandbox.presentation.ui.common

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

object SandboxCompose {

    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        group = "DarkMode"
    )
    annotation class DarkModePreview

    @Preview(
        group = "LightMode"
    )
    annotation class LightModePreview

    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        group = "DarkMode"
    )
    @Preview(
        group = "LightMode"
    )
    annotation class LightDarkModePreview
}