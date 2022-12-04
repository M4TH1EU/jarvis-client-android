package ch.broillet.jarvis.android.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.broillet.jarvis.android.audio.getAudioRecorder
import ch.broillet.jarvis.android.chat.ConversationUiState
import ch.broillet.jarvis.android.pages.DisplayMainPage
import ch.broillet.jarvis.android.pages.DisplayPermissionsPage
import ch.broillet.jarvis.android.pages.DisplaySettingsPage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Navigation(uiState: ConversationUiState) {
    val navController = rememberNavController()

    val permissions = listOf(
        android.Manifest.permission.RECORD_AUDIO,
    )
    val permissionState = rememberMultiplePermissionsState(permissions)


    NavHost(
        navController = navController,
        startDestination = if (permissionState.allPermissionsGranted) Screen.MainScreen.route else Screen.PermissionsScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, uiState)
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = Screen.PermissionsScreen.route) {
            PermissionsScreen(navController = navController)
        }
    }

}

@Composable
fun MainScreen(navController: NavController, uiState: ConversationUiState) {
    //TODO: change so that it doesn't reset when going to settings menu
    DisplayMainPage(
        navController,
        uiState,
        getAudioRecorder(LocalContext.current)
    )
}

@Composable
fun SettingsScreen(navController: NavController) {
    DisplaySettingsPage(navController)
}

@Composable
fun PermissionsScreen(navController: NavController) {
    DisplayPermissionsPage(navController)
}