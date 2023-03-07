package com.example.androidsandbox.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.androidsandbox.R
import com.example.androidsandbox.presentation.helper.LifecycleLogger
import com.example.androidsandbox.presentation.helper.LifecycleLoggerImpl
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), LifecycleLogger by LifecycleLoggerImpl() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleOwner(this, ListFragment::class.simpleName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AndroidSandboxTheme {
                    val viewModel by viewModels<ListViewModel>()
                    val uiState by viewModel.uiState.collectAsState()
                    val uiEvent = viewModel.uiEvent

                    ListScreen(
                        uiState = uiState,
                        uiActionHandler = viewModel::uiActionHandler
                    )

                    val context = LocalContext.current
                    LaunchedEffect(uiEvent) {
                        uiEvent.collect { uiAction ->
                            when (uiAction) {
                                is ListUiEvent.ShowToast -> {
                                    Toast.makeText(context, uiAction.message, Toast.LENGTH_SHORT)
                                        .show()
                                }

                                is ListUiEvent.NavigateToDetails -> {
                                    findNavController().navigate(
                                        R.id.action_listFragment_to_detailsFragment,
                                        bundleOf("item_id" to uiAction.sandboxItem.id)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}