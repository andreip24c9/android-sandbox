package com.example.androidsandbox.repository

import com.example.androidsandbox.domain.SandboxItem

interface SandboxRepository {
    suspend fun fetchSandboxItems(query: String?) : List<SandboxItem>
}