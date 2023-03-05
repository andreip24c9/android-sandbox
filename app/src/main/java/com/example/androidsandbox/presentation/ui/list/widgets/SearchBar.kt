package com.example.androidsandbox.presentation.ui.list.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String = "",
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    Surface(modifier = modifier) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val searchQuery = rememberSaveable { mutableStateOf(query) }

        TextField(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            value = query,
            maxLines = 1,
            onValueChange = {
                searchQuery.value = it
                onValueChange(it)
            },
            placeholder = {
                Text(text = "Search")
            },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            },
            trailingIcon = {
                if (searchQuery.value.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable {
                            searchQuery.value = ""
                            onClearClick()
                        },
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClick()
                keyboardController?.hide()
            })
        )
    }
}

@Preview
@Composable
fun PreviewSearchBarLight() {
    AndroidSandboxTheme {
        SearchBar(onValueChange = {}, onSearchClick = {}, onClearClick = {})
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchBarDark() {
    AndroidSandboxTheme {
        SearchBar(onValueChange = {}, onSearchClick = {}, onClearClick = {})
    }
}