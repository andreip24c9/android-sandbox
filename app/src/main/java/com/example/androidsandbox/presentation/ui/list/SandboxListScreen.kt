package com.example.androidsandbox.presentation.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsandbox.domain.SandboxItem
import com.example.androidsandbox.presentation.ui.list.components.SandboxList
import com.example.androidsandbox.presentation.ui.list.components.SearchBar
import com.example.androidsandbox.presentation.ui.list.components.TitleTextLoader
import com.example.androidsandbox.presentation.ui.list.components.getPreviewSandboxItems
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SandboxListScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    uiEvent: UiEvent,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = { TitleTextLoader(text = "Sandbox App", isLoading = uiState.isLoading) }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = uiState.searchQuery,
                    onValueChange = { uiEvent.onSearchQueryChange(it) },
                    onSearchClick = { uiEvent.onSearchClick() },
                    onClearClick = { uiEvent.onClearClick() })
                SandboxList(
                    list = uiState.sandboxItems,
                    onTaskCheckChange = { task, isChecked ->
                        uiEvent.onItemCheckChange(task, isChecked)
                    },
                    onCloseTask = { task -> uiEvent.onItemCloseClick(task) })
            }
        }
    )
}

@Preview
@Composable
fun PreviewMainScreenLight() {
    AndroidSandboxTheme {
        SandboxListScreen(
            uiState = UiState(
                searchQuery = "testQuery",
                sandboxItems = getPreviewSandboxItems(),
                isLoading = true
            ),
            uiEvent = object : UiEvent {
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
fun PreviewMainScreenDark() {
    AndroidSandboxTheme {
        SandboxListScreen(
            uiState = UiState(
                searchQuery = "testQuery",
                sandboxItems = getPreviewSandboxItems(),
                isLoading = true
            ),
            uiEvent = object : UiEvent {
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