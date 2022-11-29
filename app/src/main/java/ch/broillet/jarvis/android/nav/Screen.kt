package ch.broillet.jarvis.android.nav

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object SettingsScreen : Screen("settings_screen")
    object PermissionsScreen : Screen("permissions_screen")
}