package com.example.androidsandbox.presentation.ui.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.androidsandbox.presentation.ui.common.navigation.animations.composableWithDefaultAnimation
import com.example.androidsandbox.presentation.ui.details.DetailsScreenRoute

const val DetailsScreenRoute = "details_screen"
internal const val itemIdArg = "itemId"

fun NavController.navigateToDetailsScreen(id: String) {
    navigate("$DetailsScreenRoute/$id")
}

fun NavGraphBuilder.detailsScreen(onBackClick: () -> Unit) {
    composableWithDefaultAnimation(
        route = "$DetailsScreenRoute/{$itemIdArg}",
        arguments = listOf(navArgument(itemIdArg) {
            type = NavType.StringType
            defaultValue = ""
            nullable = false
        })
    ) {
        DetailsScreenRoute(
            onBackClick = onBackClick
        )
    }
}