package com.example.androidsandbox.presentation.ui.list.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsandbox.domain.SandboxItem
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SandboxList(
    modifier: Modifier = Modifier,
    list: List<SandboxItem>,
    onItemClick: (SandboxItem) -> Unit,
    onItemLongClick: (SandboxItem) -> Unit,
    onItemCheckChange: (SandboxItem, Boolean) -> Unit,
    onCloseClick: (SandboxItem) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(list, key = { item -> item.id }) { item ->
            ListItem(
                modifier = Modifier.animateItemPlacement(),
                itemName = item.label,
                itemDescription = item.description,
                checked = item.checked,
                onClick = { onItemClick(item) },
                onLongClick = { onItemLongClick(item) },
                onCheckChange = { checked -> onItemCheckChange(item, checked) },
                onClose = { onCloseClick(item) }
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewWellnessTaskListDark() {
    AndroidSandboxTheme {
        SandboxList(
            list = getPreviewSandboxItems(),
            onCloseClick = {},
            onItemClick = {},
            onItemLongClick = {},
            onItemCheckChange = { _, _ -> })
    }
}

@Preview
@Composable
fun PreviewWellnessTaskListLight() {
    AndroidSandboxTheme {
        SandboxList(
            list = getPreviewSandboxItems(),
            onCloseClick = {},
            onItemClick = {},
            onItemLongClick = {},
            onItemCheckChange = { _, _ -> })
    }
}

internal fun getPreviewSandboxItems() = List(30) {
    SandboxItem(
        "$it",
        "Item ${it + 1}",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        false
    )
}