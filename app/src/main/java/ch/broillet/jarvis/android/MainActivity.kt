package ch.broillet.jarvis.android

import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import ch.broillet.jarvis.android.chat.ConversationUiState
import ch.broillet.jarvis.android.chat.Message
import ch.broillet.jarvis.android.nav.Navigation
import ch.broillet.jarvis.android.ui.theme.JarvisclientappTheme
import ch.broillet.jarvis.android.utils.SocketHandler


class MainActivity : ComponentActivity() {


    // Default uiState with welcome message only
    var uiState = ConversationUiState(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        uiState = ConversationUiState(
            listOf(
                Message(
                    true,
                    resources.getString(R.string.demo_message_1)
                )
            )
        )

        // The following lines connects the Android app to the server.
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        SocketHandler.joinRoom(
            Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        )

        SocketHandler.getSocket()
            .on("message_from_jarvis") { SocketHandler.messageFromJarvis(it, uiState) }

        super.onCreate(savedInstanceState)

        setContent {
            JarvisclientappTheme {

                // Fullscreen
                WindowCompat.setDecorFitsSystemWindows(window, false)
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(uiState)
                }
            }
        }
    }
}