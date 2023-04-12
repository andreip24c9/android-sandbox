package com.example.androidsandbox.presentation.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.androidsandbox.presentation.helper.LifecycleLogger
import com.example.androidsandbox.presentation.helper.LifecycleLoggerImpl
import com.example.androidsandbox.presentation.ui.common.navigation.BottomNavigationScreen
import com.example.androidsandbox.presentation.ui.common.navigation.TabScreen
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LifecycleLogger by LifecycleLoggerImpl() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleOwner(this, MainActivity::class.simpleName)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            AndroidSandboxTheme {
                BottomNavigationScreen(*TabScreen.values())
            }
        }
    }
}