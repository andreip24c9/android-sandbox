package com.example.androidsandbox.presentation.ui.list.navigation

import androidx.navigation.*
import com.example.androidsandbox.presentation.ui.common.navigation.animations.composableWithDefaultAnimation
import com.example.androidsandbox.presentation.ui.list.ListScreenRoute

const val ListScreenRoute = "list_screen"

fun NavController.navigateToListScreen() {
    navigate(ListScreenRoute)
}

fun NavGraphBuilder.listScreen(onItemClick: (String) -> Unit) {
    composableWithDefaultAnimation(route = ListScreenRoute) {
        ListScreenRoute(onItemClick)
    }
}