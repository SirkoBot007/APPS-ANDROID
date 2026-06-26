package com.sirko007.smartnotes.data.repository

import com.sirko007.smartnotes.data.remote.AnthropicApi
import com.sirko007.smartnotes.data.remote.dto.Message
import com.sirko007.smartnotes.data.remote.dto.MessageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/** Result wrapper so the ViewModel can render loading/success/error cleanly. */
sealed interface AiResult {
    data class Success(val text: String) : AiResult
    data class Error(val message: String) : AiResult
}

/** The kind of AI action the user can run on a note. */
enum class AiAction(val instruction: String) {
    SUMMARIZE("Resume la siguiente nota en 3 viñetas concisas. Responde en español."),
    IMPROVE("Reescribe la siguiente nota con una redacción más clara y profesional, manteniendo el significado. Responde en español."),
    EXPAND("Amplía la siguiente nota en un párrafo bien estructurado y con más detalle. Responde en español.")
}

@Singleton
class AiRepository @Inject constructor(
    private val api: AnthropicApi
) {
    private val model = "claude-haiku-4-5"

    suspend fun run(action: AiAction, noteText: String): AiResult = withContext(Dispatchers.IO) {
        if (noteText.isBlank()) {
            return@withContext AiResult.Error("La nota está vacía: escribe algo primero.")
        }
        try {
            val response = api.sendMessage(
                MessageRequest(
                    model = model,
                    maxTokens = 1024,
                    system = "Eres un asistente de escritura conciso integrado en una app de notas. Respondes siempre en español.",
                    messages = listOf(
                        Message(role = "user", content = "${action.instruction}\n\n---\n$noteText")
                    )
                )
            )
            val text = response.content.firstOrNull { it.type == "text" }?.text
            if (text.isNullOrBlank()) {
                AiResult.Error("El asistente devolvió una respuesta vacía.")
            } else {
                AiResult.Success(text.trim())
            }
        } catch (e: Exception) {
            AiResult.Error(e.message ?: "Error de red. Comprueba tu clave de API y la conexión.")
        }
    }
}
