package com.sirko007.smartnotes.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Request/response models for the Anthropic Messages API.
 * Docs: https://docs.anthropic.com/en/api/messages
 */
@JsonClass(generateAdapter = true)
data class MessageRequest(
    val model: String,
    @Json(name = "max_tokens") val maxTokens: Int,
    val system: String? = null,
    val messages: List<Message>
)

@JsonClass(generateAdapter = true)
data class Message(
    val role: String,
    val content: String
)

@JsonClass(generateAdapter = true)
data class MessageResponse(
    val id: String?,
    val role: String?,
    val content: List<ContentBlock> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ContentBlock(
    val type: String,
    val text: String?
)
