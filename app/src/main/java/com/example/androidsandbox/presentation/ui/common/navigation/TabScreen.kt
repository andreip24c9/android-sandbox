package com.example.androidsandbox.presentation.ui.common.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.androidsandbox.R
import com.example.androidsandbox.presentation.ui.list.navigation.ListScreenRoute

//sealed class TabScreen(
//    val route: String, val icon: ImageVector, @StringRes val labelRes: Int
//) {
//    object ListTabScreen : TabScreen(
//        route = ListScreenRoute, Icons.Default.List, R.string.first_tab_label
//    )
//
//    object SecondTabScreen : TabScreen(
//        route = "second_screen", Icons.Default.Settings, R.string.second_tab_label
//    )
//
//    object ThirdTabScreen : TabScreen(
//        route = "third_screen", Icons.Default.Favorite, R.string.third_tab_label
//    )
//
//    companion object {
//        val routes = listOf(
//            ListTabScreen.route,
//            SecondTabScreen.route,
//            ThirdTabScreen.route
//        )
//    }
//}

enum class TabScreen(val route: String, val icon: ImageVector, @StringRes val labelRes: Int) {
    ListTabScreen(route = ListScreenRoute, Icons.Default.List, R.string.first_tab_label),
    SecondTabScreen(route = "second_screen", Icons.Default.Settings, R.string.second_tab_label),
    ThirdTabScreen(route = "third_screen", Icons.Default.Favorite, R.string.third_tab_label)
}
