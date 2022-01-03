package ch.mathieubroillet.jarvis.android.nav

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object SettingsScreen : Screen("settings_screen")
}