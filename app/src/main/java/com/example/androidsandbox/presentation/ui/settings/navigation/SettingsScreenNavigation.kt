package com.example.androidsandbox.presentation.ui.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.androidsandbox.presentation.ui.common.navigation.animations.composableWithDefaultAnimation
import com.example.androidsandbox.presentation.ui.settings.SettingsScreenRoute

const val SettingsScreenRoute = "settings_screen"

fun NavController.navigateToSettingsScreen() {
    navigate(SettingsScreenRoute)
}

fun NavGraphBuilder.settingsScreen() {
    composableWithDefaultAnimation(route = SettingsScreenRoute) {
        SettingsScreenRoute()
    }
}