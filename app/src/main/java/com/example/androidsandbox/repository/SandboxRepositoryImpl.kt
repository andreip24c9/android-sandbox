package com.example.androidsandbox.repository

import com.example.androidsandbox.domain.SandboxItem
import kotlinx.coroutines.delay

class SandboxRepositoryImpl : SandboxRepository {

    private val itemList = MutableList(40) { SandboxItem("$it", "Item ${it + 1}", false) }

    override suspend fun fetchSandboxItems(query: String?): List<SandboxItem> {
        delay(750) // simulate network request
        return itemList.takeUnless { query.isNullOrEmpty() }
            ?.filter { it.label.contains(query!!, true) }
            ?: itemList
    }

    override suspend fun checkSandboxItem(itemId: String, isChecked: Boolean): SandboxItem? {
        delay(750) // simulate network request
        return itemList.find { it.id == itemId }?.apply { checked = isChecked }
    }

    override suspend fun deleteSandboxItem(itemId: String): SandboxItem? {
        delay(750) // simulate network request
        val deletedItem = itemList.find { it.id == itemId }
        itemList.remove(deletedItem)
        return deletedItem
    }
}