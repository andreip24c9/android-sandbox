package com.example.androidsandbox.presentation.ui.common.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.androidsandbox.R
import com.example.androidsandbox.presentation.ui.favorites.navigation.FavoritesScreenRoute
import com.example.androidsandbox.presentation.ui.list.navigation.ListScreenRoute
import com.example.androidsandbox.presentation.ui.settings.navigation.SettingsScreenRoute

enum class TabScreen(val route: String, val icon: ImageVector, @StringRes val labelRes: Int) {
    ListTabScreen(route = ListScreenRoute, Icons.Default.List, R.string.list_tab_label),
    FavoritesTabScreen(route = FavoritesScreenRoute, Icons.Default.Favorite, R.string.favorites_tab_label),
    SettingsTabScreen(route = SettingsScreenRoute, Icons.Default.Settings, R.string.settings_tab_label)
}
