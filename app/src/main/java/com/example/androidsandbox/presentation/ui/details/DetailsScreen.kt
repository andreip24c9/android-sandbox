package com.example.androidsandbox.presentation.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidsandbox.presentation.ui.common.SandboxCompose
import com.example.androidsandbox.presentation.ui.common.widgets.SandboxToolbarWithCheckbox
import com.example.androidsandbox.presentation.ui.list.widgets.SandboxFullPageError
import com.example.androidsandbox.presentation.ui.list.widgets.SandboxFullPageLoader
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme


@Composable
fun DetailsScreenRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreen(
        modifier = modifier,
        uiState = uiState,
        onCheckChanged = viewModel::onCheckChange,
        onBackClick = onBackClick
    )
}

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsScreenUiState,
    onCheckChanged: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SandboxToolbarWithCheckbox(
                title = (uiState as? DetailsScreenUiState.Data)?.title ?: "",
                navigationAction = onBackClick,
                checkboxEnabled = uiState is DetailsScreenUiState.Data,
                checked = (uiState as? DetailsScreenUiState.Data)?.checked ?: false,
                onCheckChange = onCheckChanged
            )
        }) { paddingValues ->
        Surface {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                when (uiState) {
                    is DetailsScreenUiState.Data -> {
                        Text(text = uiState.description, modifier = Modifier.padding(16.dp))
                    }
                    DetailsScreenUiState.Error -> {
                        SandboxFullPageError(
                            modifier = Modifier.fillMaxSize(),
                            errorLabel = "Error"
                        )
                    }
                    DetailsScreenUiState.Loading -> {
                        SandboxFullPageLoader(
                            modifier = Modifier.fillMaxSize(),
                            loadingLabel = "Loading ..."
                        )
                    }
                }
            }
        }
    }
}

@Composable
@SandboxCompose.LightDarkModePreview
private fun previewDetailsScreen() {
    AndroidSandboxTheme {
        DetailsScreen(
            uiState = DetailsScreenUiState.Data(
                title = "Preview Title",
                description = "Description",
                checked = false
            ),
            onCheckChanged = {},
            onBackClick = {}
        )
    }
}