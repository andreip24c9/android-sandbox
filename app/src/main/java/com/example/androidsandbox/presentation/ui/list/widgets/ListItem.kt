package com.example.androidsandbox.presentation.ui.list.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    itemName: String,
    itemDescription: String,
    checked: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onCheckChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    expanded: Boolean = false
) {
    var isExpanded by rememberSaveable { mutableStateOf(expanded) }

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .combinedClickable(
                onClick = onClick, onLongClick = {
                    isExpanded = !isExpanded
                    onLongClick()
                }
            )
            .padding(all = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                modifier = Modifier.size(28.dp),
                checked = checked,
                onCheckedChange = onCheckChange
            )
            Text(
                text = itemName,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = { isExpanded = !isExpanded }
                    )
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

        if (isExpanded) {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    text = itemDescription
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewWellnessTaskDarkExpanded() {
    AndroidSandboxTheme {
        PreviewItem(expanded = true)
    }
}

@Preview
@Composable
fun PreviewWellnessTaskLightExpanded() {
    AndroidSandboxTheme {
        PreviewItem(expanded = true)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewWellnessTaskDarkCollapsed() {
    AndroidSandboxTheme {
        PreviewItem(expanded = false)
    }
}

@Preview
@Composable
fun PreviewWellnessTaskLightCollapsed() {
    AndroidSandboxTheme {
        PreviewItem(expanded = false)
    }
}

@Composable
private fun PreviewItem(expanded: Boolean) =
    ListItem(
        itemName = "Item label",
        itemDescription = "Item Description",
        onClose = {},
        onClick = {},
        onLongClick = {},
        onCheckChange = {},
        expanded = expanded
    )

