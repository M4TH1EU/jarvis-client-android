package ch.broillet.jarvis.android.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DefaultBox(
    content: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 45.dp, bottom = 10.dp)
    ) {
        content()
    }

}

// Open the application in the system's settings to (i.e) manually give permission
fun openAppSettings(context: Context) {
    context.startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri
                .fromParts("package", context.applicationInfo.packageName, null)
        )
    )
}
