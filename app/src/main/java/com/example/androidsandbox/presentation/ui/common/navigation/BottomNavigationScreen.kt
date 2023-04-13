package com.example.androidsandbox.presentation.ui.common.navigation

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.androidsandbox.presentation.ui.common.SandboxCompose
import com.example.androidsandbox.presentation.ui.details.navigation.detailsScreen
import com.example.androidsandbox.presentation.ui.details.navigation.navigateToDetailsScreen
import com.example.androidsandbox.presentation.ui.list.navigation.listScreen
import com.example.androidsandbox.presentation.ui.favorites.navigation.favoritesScreen
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme
import com.example.androidsandbox.presentation.ui.settings.navigation.settingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigationScreen(vararg screens: TabScreen) {
    val navController = rememberAnimatedNavController()

    var isDifferentScreenOpen by rememberSaveable { mutableStateOf(false) }
    val keyboardState by keyboardAsState()
    val shouldShowBottomNavigation = !isDifferentScreenOpen && keyboardState == Keyboard.Closed

    val alphaValue by animateFloatAsState(targetValue = if (shouldShowBottomNavigation) 1F else 0F)
    Scaffold(bottomBar = {
        AnimatedVisibility(
            visible = shouldShowBottomNavigation,
            enter = slideInVertically(animationSpec = tween(250), initialOffsetY = { 150 }),
            exit = slideOutVertically(animationSpec = tween(250), targetOffsetY = { 150 })
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
        val paddingValues by animateDpAsState(targetValue = if (shouldShowBottomNavigation) innerPadding.calculateBottomPadding() else 0.dp)

        AnimatedNavHost(
            navController = navController,
            startDestination = TabScreen.ListTabScreen.route,
            modifier = Modifier.padding(bottom = paddingValues)
        ) {
            listScreen(onItemClick = {
                isDifferentScreenOpen = true
                navController.navigateToDetailsScreen(id = it)
            })
            detailsScreen(onBackClick = {
                isDifferentScreenOpen = false
                navController.popBackStack()
            })
            favoritesScreen()
            settingsScreen()
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

enum class Keyboard {
    Opened, Closed
}

@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}

@SandboxCompose.LightDarkModePreview
@Composable
fun BottomTabNavigationScreen() {
    AndroidSandboxTheme {
        BottomNavigationScreen(
            TabScreen.ListTabScreen,
            TabScreen.FavoritesTabScreen,
        )
    }
}