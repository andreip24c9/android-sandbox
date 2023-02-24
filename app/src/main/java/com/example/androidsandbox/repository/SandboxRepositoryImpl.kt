package com.example.androidsandbox.repository

import android.util.Log
import com.example.androidsandbox.domain.SandboxItem
import kotlinx.coroutines.delay
import kotlin.math.log

class SandboxRepositoryImpl : SandboxRepository {

    private val itemList = MutableList(40) { SandboxItem("$it", "Item ${it + 1}", false) }
    private var isFirstRequest = true

    override suspend fun fetchSandboxItems(query: String?): List<SandboxItem> {
        Log.d("LoadingLogs", "fetchSandboxItems: ")
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
        Log.d("MyLogs", "deleteSandboxItem, before delay: $itemId")
        delay(1050) // simulate network request
        Log.d("MyLogs", "deleteSandboxItem afterDelay: $itemId")
        val deletedItem = itemList.find { it.id == itemId }
        itemList.remove(deletedItem)
        Log.d("MyLogs", "deleteSandboxItem returnId: ${deletedItem?.id}")
        return deletedItem
    }
}