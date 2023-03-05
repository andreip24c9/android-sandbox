package com.example.androidsandbox.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidsandbox.presentation.helper.LifecycleLogger
import com.example.androidsandbox.presentation.helper.LifecycleLoggerImpl
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(), LifecycleLogger by LifecycleLoggerImpl() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleOwner(this, DetailsFragment::class.simpleName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AndroidSandboxTheme {
                    DetailsScreen(
                        uiState = DetailsScreenUiState.Data("Test Title", false),
                        uiActions = object :DetailsScreenUiActions {
                            override fun onNavigateBackClick() {
                                findNavController().popBackStack()
                            }

                            override fun onCheckChange(checked: Boolean) {
                                // todo impl later
                            }
                        }
                    )
                }
            }
        }
    }
}