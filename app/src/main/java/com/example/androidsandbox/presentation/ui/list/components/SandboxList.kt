package com.example.androidsandbox.presentation.ui.list.components

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
    onTaskCheckChange: (SandboxItem, Boolean) -> Unit,
    onCloseTask: (SandboxItem) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(list, key = { task -> task.id }) { task ->
            SandboxListItem(
                modifier = Modifier.animateItemPlacement(),
                taskName = task.label,
                checked = task.checked,
                onCheckChange = { checked -> onTaskCheckChange(task, checked) },
                onClose = { onCloseTask(task) })
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewWellnessTaskListDark() {
    AndroidSandboxTheme {
        SandboxList(
            list = getPreviewSandboxItems(),
            onCloseTask = {},
            onTaskCheckChange = { _, _ -> })
    }
}

@Preview
@Composable
fun PreviewWellnessTaskListLight() {
    AndroidSandboxTheme {
        SandboxList(
            list = getPreviewSandboxItems(),
            onCloseTask = {},
            onTaskCheckChange = { _, _ -> })
    }
}

internal fun getPreviewSandboxItems() = List(30) { SandboxItem("$it", "Item ${it + 1}", false) }