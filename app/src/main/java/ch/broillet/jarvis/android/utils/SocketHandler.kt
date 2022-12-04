package ch.broillet.jarvis.android.utils

import ch.broillet.jarvis.android.chat.ConversationUiState
import ch.broillet.jarvis.android.chat.Message
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException

object SocketHandler {

    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket("https://jarvis-server.broillet.ch")
        } catch (_: URISyntaxException) {
        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }

    @Synchronized
    fun processMessage(message: String, uuid: String) {
        val body = JSONObject()
        body.put("data", message)
        body.put("uuid", uuid)
        getSocket().emit("process_message", body.toString())
    }

    @Synchronized
    fun joinRoom(uuid: String) {
        val body = JSONObject()
        body.put("uuid", uuid)
        getSocket().emit("join", body.toString())
    }


    fun messageFromJarvis(data: Array<Any>, uiState: ConversationUiState) {
        if (data[0].toString().contains("data")) {
            val result: JSONObject = data[0] as JSONObject
            uiState.addMessage(Message(true, result.getString("data")))
        }

    }

    fun messageFromUser(data: Array<Any>, uiState: ConversationUiState) {
        if (data[0].toString().contains("data")) {
            val result: JSONObject = data[0] as JSONObject
            uiState.addMessage(Message(false, result.getString("data")))
        }

    }
}
