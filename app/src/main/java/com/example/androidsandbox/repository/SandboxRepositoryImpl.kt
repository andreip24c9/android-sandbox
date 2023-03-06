package com.example.androidsandbox.repository

import android.util.Log
import com.example.androidsandbox.domain.SandboxItem
import kotlinx.coroutines.delay
import kotlin.math.log

class SandboxRepositoryImpl : SandboxRepository {

    private val itemList = MutableList(40) {
        SandboxItem(
            "$it",
            "Item ${it + 1}",
            "Description ${it + 1}: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            false
        )
    }
    private var isFirstRequest = true

    override suspend fun fetchSandboxItems(query: String?): List<SandboxItem> {
        delay(if (isFirstRequest) 3000 else 750) // simulate network request
        isFirstRequest = false
        return itemList.takeUnless { query.isNullOrEmpty() }
            ?.filter { it.label.contains(query!!, true) }
            ?: itemList
    }

    override suspend fun checkSandboxItem(itemId: String, isChecked: Boolean): SandboxItem? {
        delay(250) // simulate network request
        return itemList.find { it.id == itemId }?.apply { checked = isChecked }
    }

    override suspend fun deleteSandboxItem(itemId: String): SandboxItem? {
        Log.d("DeleteItemLogs", "deleteSandboxItem, before delay: $itemId")
        delay(1050) // simulate network request
        Log.d("DeleteItemLogs", "deleteSandboxItem afterDelay: $itemId")
        val deletedItem = itemList.find { it.id == itemId }
        itemList.remove(deletedItem)
        Log.d("DeleteItemLogs", "deleteSandboxItem returnId: ${deletedItem?.id}")
        return deletedItem
    }

    override suspend fun getSandboxItem(itemId: String): SandboxItem? {
        delay(1000)
        return itemList.firstOrNull { it.id == itemId }
    }
}