package ch.mathieubroillet.jarvis.android.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.mathieubroillet.jarvis.android.pages.DisplayMainPage
import ch.mathieubroillet.jarvis.android.pages.DisplayPermissionsPage
import ch.mathieubroillet.jarvis.android.pages.DisplaySettingsPage

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
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