package com.example.androidsandbox.presentation.ui.list.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SandboxListItem(
    modifier: Modifier = Modifier,
    taskName: String,
    checked: Boolean = false,
    onCheckChange: (Boolean) -> Unit,
    onClose: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .clickable { onCheckChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskName,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckChange
        )
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = onClose
            )
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewWellnessTaskDark() {
    AndroidSandboxTheme {
        SandboxListItem(taskName = "Item label", onClose = {}, onCheckChange = {})
    }
}

@Preview
@Composable
fun PreviewWellnessTaskLight() {
    AndroidSandboxTheme {
        SandboxListItem(taskName = "Item label", onClose = {}, onCheckChange = {})
    }
}

