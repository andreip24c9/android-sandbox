package com.example.androidsandbox.presentation.ui.common.navigation.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import com.example.androidsandbox.presentation.ui.common.navigation.TabScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composableWithDefaultAnimation(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route, arguments = arguments, deepLinks = deepLinks,
        enterTransition = {
            bottomNavEnterAnim()
        },
        exitTransition = {
            bottomNavExitAnim()
        },
        popEnterTransition = {
            if (!initialState.destination.hierarchy.map { it.route }
                    .any(TabScreen.values().map { it.route }::contains)) {
                bottomNavPopEnterAnim()
            } else bottomNavEnterAnim()
        },
        popExitTransition = {
            if (!initialState.destination.hierarchy.map { it.route }
                    .any(TabScreen.values().map { it.route }::contains)) {
                bottomNavPopExitAnim()
            } else bottomNavExitAnim()
        },
        content = content
    )
}


fun AnimatedContentTransitionScope<NavBackStackEntry>.bottomNavEnterAnim(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        initialOffset = { 500 },
        animationSpec = tween(250)
    ).plus(
        fadeIn(
            initialAlpha = 0f, animationSpec = tween(250)
        )
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.bottomNavExitAnim(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        targetOffset = { -500 },
        animationSpec = tween(250)
    ).plus(
        fadeOut(
            targetAlpha = 0f, animationSpec = tween(250)
        )
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.bottomNavPopEnterAnim(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        initialOffset = { -500 },
        animationSpec = tween(250)
    ).plus(
        fadeIn(
            initialAlpha = 1f, animationSpec = tween(250)
        )
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.bottomNavPopExitAnim(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        targetOffset = { 500 },
        animationSpec = tween(250)
    ).plus(
        fadeOut(
            targetAlpha = 0f, animationSpec = tween(250)
        )
    )
}