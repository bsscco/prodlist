package com.example.prodlist.ktutil.log

interface EventLogHelper {

    fun logEvent(event: String, params: Map<String, Any?> = emptyMap())
}