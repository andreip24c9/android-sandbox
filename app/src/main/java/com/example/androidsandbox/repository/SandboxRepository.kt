package com.example.androidsandbox.repository

import com.example.androidsandbox.domain.SandboxItem

interface SandboxRepository {
    suspend fun fetchSandboxItems(query: String?): List<SandboxItem>
    suspend fun checkSandboxItem(itemId: String, isChecked: Boolean): SandboxItem?
    suspend fun deleteSandboxItem(itemId: String): SandboxItem?
}