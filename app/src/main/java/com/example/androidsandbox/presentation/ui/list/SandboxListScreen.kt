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
import com.example.androidsandbox.presentation.ui.list.components.*
import com.example.androidsandbox.presentation.ui.list.components.getPreviewSandboxItems
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SandboxListScreen(
    modifier: Modifier = Modifier,
    uiState: SandboxScreenUiState,
    uiEvents: SandboxScreenUiEvents,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    TitleTextLoader(
                        text = "Sandbox App",
                        isLoading = (uiState as? SandboxScreenUiState.Data)?.isLoading ?: false
                    )
                }
            )
        },
        content = { paddingValues ->
            when (uiState) {
                is SandboxScreenUiState.Data -> {
                    Log.d("LoadingLogs", "Data state")
                    Column(modifier = Modifier.padding(paddingValues)) {
                        SearchBar(
                            modifier = Modifier.fillMaxWidth(),
                            query = uiState.searchQuery,
                            onValueChange = { uiEvents.onSearchQueryChange(it) },
                            onSearchClick = { uiEvents.onSearchClick() },
                            onClearClick = { uiEvents.onClearClick() })
                        SandboxList(
                            list = uiState.sandboxItems,
                            onTaskCheckChange = { task, isChecked ->
                                uiEvents.onItemCheckChange(task, isChecked)
                            },
                            onCloseTask = { task -> uiEvents.onItemCloseClick(task) })
                    }
                }
                SandboxScreenUiState.Loading -> {
                    Log.d("LoadingLogs", "Loading state")
                    SandboxFullPageLoader(
                        modifier = Modifier.fillMaxSize(),
                        loadingLabel = "Loading ..."
                    )
                }
                is SandboxScreenUiState.Error -> {
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
        SandboxListScreen(
            uiState = SandboxScreenUiState.Data(
                searchQuery = "testQuery",
                sandboxItems = getPreviewSandboxItems(),
                isLoading = true
            ),
            uiEvents = object : SandboxScreenUiEvents {
                override fun onSearchQueryChange(newQuery: String) {
                }

                override fun onSearchClick() {
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
        SandboxListScreen(
            uiState = SandboxScreenUiState.Data(
                searchQuery = "testQuery",
                sandboxItems = getPreviewSandboxItems(),
                isLoading = true
            ),
            uiEvents = object : SandboxScreenUiEvents {
                override fun onSearchQueryChange(newQuery: String) {
                }

                override fun onSearchClick() {
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
        SandboxListScreen(
            uiState = SandboxScreenUiState.Loading,
            uiEvents = object : SandboxScreenUiEvents {
                override fun onSearchQueryChange(newQuery: String) {
                }

                override fun onSearchClick() {
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
        SandboxListScreen(
            uiState = SandboxScreenUiState.Loading,
            uiEvents = object : SandboxScreenUiEvents {
                override fun onSearchQueryChange(newQuery: String) {
                }

                override fun onSearchClick() {
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