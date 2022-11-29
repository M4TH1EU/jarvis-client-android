package ch.broillet.jarvis.android.chat

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf

class ConversationUiState(
    initialMessages: List<Message>
) {
    private val _messages: MutableList<Message> =
        mutableStateListOf(*initialMessages.toTypedArray())
    val messages: List<Message> = _messages

    fun addMessage(msg: Message) {
        _messages.add(0, msg) // Add to the beginning of the list
    }
}

@Immutable
data class Message(
    val isJarvis: Boolean,
    val content: String,
)