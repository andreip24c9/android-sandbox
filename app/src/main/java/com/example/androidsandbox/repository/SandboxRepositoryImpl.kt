package com.example.androidsandbox.repository

import com.example.androidsandbox.domain.SandboxItem
import kotlinx.coroutines.delay

class SandboxRepositoryImpl : SandboxRepository {
    override suspend fun fetchSandboxItems(query: String?): List<SandboxItem> {
        delay(750) // simulate network request
        val itemList = List(40) { SandboxItem(it, "Item ${it + 1}", false) }
        return itemList.takeUnless { query.isNullOrEmpty() }
            ?.filter { it.label.contains(query!!, true) }
            ?: itemList
    }
}