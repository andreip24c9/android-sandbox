package com.example.androidsandbox.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.androidsandbox.presentation.helper.LifecycleLogger
import com.example.androidsandbox.presentation.helper.LifecycleLoggerImpl
import com.example.androidsandbox.presentation.helper.MyLazyDelegate
import com.example.androidsandbox.presentation.ui.list.SandboxListScreen
import com.example.androidsandbox.presentation.ui.list.SandboxViewModel
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), LifecycleLogger by LifecycleLoggerImpl() {

    val lazyObj by MyLazyDelegate { "Lazy value" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleOwner(this)

        val viewModel by viewModels<SandboxViewModel>()
//        val wellnessUiState by viewModel.wellnessUiState
        setContent {
            AndroidSandboxTheme {

                SandboxListScreen(
                    uiState = viewModel.uiState,
                    uiEvent = viewModel
                )
            }
        }
    }
}


