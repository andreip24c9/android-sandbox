package com.example.androidsandbox.presentation.ui.list

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsandbox.domain.SandboxItem
import com.example.androidsandbox.presentation.ui.list.widgets.*
import com.example.androidsandbox.presentation.ui.list.widgets.getPreviewSandboxItems
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    uiState: ListScreenUiState,
    uiActions: ListScreenUiActions,
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
                            onValueChange = { uiActions.onSearchQueryChange(it) },
                            onSearchClick = { uiActions.onSearchClick() },
                            onClearClick = { uiActions.onClearClick() })
                        SandboxList(
                            list = uiState.sandboxItems,
                            onItemClick = { item -> uiActions.onItemClick(item) },
                            onItemLongClick = { item -> uiActions.onItemLongClick(item) },
                            onItemCheckChange = { task, isChecked ->
                                uiActions.onItemCheckChange(task, isChecked)
                            },
                            onCloseClick = { task -> uiActions.onItemCloseClick(task) })
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

@Preview
@Composable
fun PreviewMainScreenLightList() {
    AndroidSandboxTheme {
        ListScreen(
            uiState = ListScreenUiState.Data(
                searchQuery = "testQuery",
                sandboxItems = getPreviewSandboxItems(),
                isLoading = true
            ),
            uiActions = object : ListScreenUiActions {
                override fun onSearchQueryChange(newQuery: String) {
                }

                override fun onSearchClick() {
                }

                override fun onItemClick(sandboxItem: SandboxItem) {
                }

                override fun onItemLongClick(sandboxItem: SandboxItem) {
                }

                override fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean) {
                }

                override fun onItemCloseClick(sandboxItem: SandboxItem) {
                }

                override fun onClearClick() {
                }
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainScreenDarkList() {
    AndroidSandboxTheme {
        ListScreen(
            uiState = ListScreenUiState.Data(
                searchQuery = "testQuery",
                sandboxItems = getPreviewSandboxItems(),
                isLoading = true
            ),
            uiActions = object : ListScreenUiActions {
                override fun onSearchQueryChange(newQuery: String) {
                }

                override fun onSearchClick() {
                }

                override fun onItemClick(sandboxItem: SandboxItem) {
                }

                override fun onItemLongClick(sandboxItem: SandboxItem) {
                }

                override fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean) {
                }

                override fun onItemCloseClick(sandboxItem: SandboxItem) {
                }

                override fun onClearClick() {
                }
            }
        )
    }
}


@Preview
@Composable
fun PreviewMainScreenLightLoading() {
    AndroidSandboxTheme {
        ListScreen(
            uiState = ListScreenUiState.Loading,
            uiActions = object : ListScreenUiActions {
                override fun onSearchQueryChange(newQuery: String) {
                }

                override fun onSearchClick() {
                }

                override fun onItemClick(sandboxItem: SandboxItem) {
                }

                override fun onItemLongClick(sandboxItem: SandboxItem) {
                }

                override fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean) {
                }

                override fun onItemCloseClick(sandboxItem: SandboxItem) {
                }

                override fun onClearClick() {
                }
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainScreenDarkLoading() {
    AndroidSandboxTheme {
        ListScreen(
            uiState = ListScreenUiState.Loading,
            uiActions = object : ListScreenUiActions {
                override fun onSearchQueryChange(newQuery: String) {
                }

                override fun onSearchClick() {
                }

                override fun onItemClick(sandboxItem: SandboxItem) {
                }

                override fun onItemLongClick(sandboxItem: SandboxItem) {
                }

                override fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean) {
                }

                override fun onItemCloseClick(sandboxItem: SandboxItem) {
                }

                override fun onClearClick() {
                }
            }
        )
    }
}