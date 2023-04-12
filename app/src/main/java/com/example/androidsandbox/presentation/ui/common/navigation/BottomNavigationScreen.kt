package com.example.androidsandbox.presentation.ui.common.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.androidsandbox.presentation.ui.common.SandboxCompose
import com.example.androidsandbox.presentation.ui.common.navigation.animations.composableWithDefaultAnimation
import com.example.androidsandbox.presentation.ui.details.navigation.detailsScreen
import com.example.androidsandbox.presentation.ui.details.navigation.navigateToDetailsScreen
import com.example.androidsandbox.presentation.ui.list.navigation.listScreen
import com.example.androidsandbox.presentation.ui.second_screen.SecondScreen
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme
import com.example.androidsandbox.presentation.ui.third_screen.ThirdScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigationScreen(vararg screens: TabScreen) {
    val navController = rememberAnimatedNavController()

    var isDifferentScreenOpen by rememberSaveable { mutableStateOf(false) }
    val alphaValue by animateFloatAsState(targetValue = if (isDifferentScreenOpen) 0F else 1F)

    Scaffold(bottomBar = {
        AnimatedVisibility(
            visible = !isDifferentScreenOpen,
            enter = slideInVertically(animationSpec = tween(250), initialOffsetY = { 150 }),
            exit = slideOutVertically(animationSpec = tween(150), targetOffsetY = { 150 })
        ) {
            NavigationBar(modifier = Modifier.alpha(alphaValue)) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { screen ->
                    NavigationBarItem(icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.labelRes)) },
                        selected = currentDestination?.hierarchy?.any { it.route?.contains(screen.route) == true } == true,
                        onClick = { navController.navigateTab(screen.route) })
                }
            }
        }
    }) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = TabScreen.ListTabScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            listScreen(onItemClick = {
                isDifferentScreenOpen = true
                navController.navigateToDetailsScreen(id = it)
            })
            composableWithDefaultAnimation(TabScreen.SecondTabScreen.route) {
                SecondScreen(Modifier.fillMaxSize())
            }
            composableWithDefaultAnimation(TabScreen.ThirdTabScreen.route) {
                ThirdScreen(Modifier.fillMaxSize())
            }
            detailsScreen(onBackClick = {
                navController.popBackStack()
                isDifferentScreenOpen = false
            })
        }
    }
}

internal fun NavController.navigateTab(route: String) {
    navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // re-selecting the same item
        launchSingleTop = true
        // Restore state when re-selecting a previously selected item
        restoreState = true
    }
}

@SandboxCompose.LightDarkModePreview
@Composable
fun BottomTabNavigationScreen() {
    AndroidSandboxTheme {
        BottomNavigationScreen(
            TabScreen.ListTabScreen,
            TabScreen.SecondTabScreen,
        )
    }
}