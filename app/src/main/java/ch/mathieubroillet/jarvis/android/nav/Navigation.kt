package ch.mathieubroillet.jarvis.android.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.mathieubroillet.jarvis.android.pages.DisplayMainPage
import ch.mathieubroillet.jarvis.android.pages.DisplayPermissionsPage
import ch.mathieubroillet.jarvis.android.pages.DisplaySettingsPage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    val permissions = listOf(android.Manifest.permission.RECORD_AUDIO)
    val permissionState = rememberMultiplePermissionsState(permissions)


    NavHost(
        navController = navController,
        startDestination = if (permissionState.allPermissionsGranted) Screen.MainScreen.route else Screen.PermissionsScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
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
fun MainScreen(navController: NavController) {
    DisplayMainPage(navController)
}

@Composable
fun SettingsScreen(navController: NavController) {
    DisplaySettingsPage(navController)
}

@Composable
fun PermissionsScreen(navController: NavController) {
    DisplayPermissionsPage(navController)
}