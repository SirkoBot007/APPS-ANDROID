package com.sirko007.smartnotes.data.remote

import com.sirko007.smartnotes.data.remote.dto.MessageRequest
import com.sirko007.smartnotes.data.remote.dto.MessageResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AnthropicApi {

    @POST("v1/messages")
    suspend fun sendMessage(@Body request: MessageRequest): MessageResponse
}
