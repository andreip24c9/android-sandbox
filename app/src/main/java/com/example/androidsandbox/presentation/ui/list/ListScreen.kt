package com.example.androidsandbox.presentation.ui.list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidsandbox.domain.SandboxItem
import com.example.androidsandbox.presentation.ui.common.SandboxCompose
import com.example.androidsandbox.presentation.ui.list.widgets.*
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme


@Composable
internal fun ListScreenRoute(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent = viewModel.uiEvent

    ListScreen(
        modifier = modifier,
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onSearchClick = viewModel::onSearchClick,
        onClearClick = viewModel::onClearClick,
        onItemClick = viewModel::onItemClick,
        onItemLongClick = viewModel::onItemLongClick,
        onItemCheckChange = viewModel::onItemCheckChange,
        onDeleteClick = viewModel::onDeleteClick,
    )

    val context = LocalContext.current
    LaunchedEffect(uiEvent) {
        uiEvent.collect { uiAction ->
            when (uiAction) {
                is ListUiEvent.ShowToast -> {
//                    Toast.makeText(context, uiAction.message, Toast.LENGTH_SHORT)
//                        .show()
                }

                is ListUiEvent.NavigateToDetails -> {
                    onItemClick(uiAction.sandboxItem.id)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    uiState: ListScreenUiState,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit,
    onItemClick: (SandboxItem) -> Unit,
    onItemLongClick: (SandboxItem) -> Unit,
    onItemCheckChange: (SandboxItem, Boolean) -> Unit,
    onDeleteClick: (SandboxItem) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    TitleTextLoader(
                        text = "Sandbox App",
                        isLoading = (uiState as? ListScreenUiState.Data)?.isLoading ?: false
                    )
                }
            )
        },
        content = { paddingValues ->
            when (uiState) {
                is ListScreenUiState.Data -> {
                    Log.d("LoadingLogs", "Data state")
                    Column(modifier = Modifier.padding(paddingValues)) {
                        SearchBar(
                            modifier = Modifier.fillMaxWidth(),
                            query = uiState.searchQuery,
                            onValueChange = onQueryChange,
                            onSearchClick = onSearchClick,
                            onClearClick = onClearClick
                        )
                        SandboxList(
                            list = uiState.sandboxItems,
                            onItemClick = onItemClick,
                            onItemLongClick = onItemLongClick,
                            onItemCheckChange = onItemCheckChange,
                            onCloseClick = onDeleteClick
                        )
                    }
                }
                ListScreenUiState.Loading -> {
                    Log.d("LoadingLogs", "Loading state")
                    SandboxFullPageLoader(
                        modifier = Modifier.fillMaxSize(),
                        loadingLabel = "Loading ..."
                    )
                }
                is ListScreenUiState.Error -> {
                    SandboxFullPageError(
                        modifier = Modifier.fillMaxSize(),
                        errorLabel = uiState.errorLabel
                    )
                }
            }
        }
    )
}

@SandboxCompose.LightDarkModePreview
@Composable
fun PreviewMainScreen(@PreviewParameter(ListScreenUiStateProvider::class) uiState: ListScreenUiState) {
    AndroidSandboxTheme {
        ListScreen(
            uiState = uiState,
            onQueryChange = { },
            onSearchClick = { },
            onClearClick = { },
            onItemClick = { },
            onItemLongClick = { },
            onItemCheckChange = { _, _ -> },
            onDeleteClick = { },
        )
    }
}

internal class ListScreenUiStateProvider : PreviewParameterProvider<ListScreenUiState> {
    override val values: Sequence<ListScreenUiState> = sequenceOf(
        ListScreenUiState.Data(
            searchQuery = "testQuery",
            sandboxItems = getPreviewSandboxItems(),
            isLoading = true
        ),
        ListScreenUiState.Loading,
        ListScreenUiState.Error(
            errorLabel = "Error title"
        ),
    )
}