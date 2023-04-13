package com.example.androidsandbox.presentation.ui.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.androidsandbox.presentation.ui.common.navigation.animations.composableWithDefaultAnimation
import com.example.androidsandbox.presentation.ui.favorites.FavoritesScreenRoute

const val FavoritesScreenRoute = "favorites_screen"

fun NavController.navigateToFavoritesScreen() {
    navigate(FavoritesScreenRoute)
}

fun NavGraphBuilder.favoritesScreen() {
    composableWithDefaultAnimation(route = FavoritesScreenRoute) {
        FavoritesScreenRoute()
    }
}