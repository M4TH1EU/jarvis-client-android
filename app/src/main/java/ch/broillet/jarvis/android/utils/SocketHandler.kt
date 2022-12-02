package ch.broillet.jarvis.android.utils

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
}