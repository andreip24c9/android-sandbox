package com.example.androidsandbox.presentation.ui.list.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateSizeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidsandbox.presentation.ui.common.SandboxCompose
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
    val color by animateColorAsState(targetValue = if (isExpanded) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.background)
    val iconSize by animateDpAsState(targetValue = if (isExpanded) 32.dp else 24.dp)
    val textSize by animateFloatAsState(targetValue = if (isExpanded) 24f else 18f)

    Column(
        modifier = modifier
            .background(color = color)
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
                fontSize = textSize.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(iconSize)
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
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = onClose
                    )
                    .size(iconSize)
            )
        }

        AnimatedVisibility(visible = isExpanded) {
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

@SandboxCompose.LightDarkModePreview
@Composable
fun PreviewItemExpanded() {
    AndroidSandboxTheme {
        PreviewItem(expanded = true)
    }
}

@SandboxCompose.LightDarkModePreview
@Composable
fun PreviewItemCollapsed() {
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

