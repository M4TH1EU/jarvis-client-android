package ch.mathieubroillet.jarvis.android.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState


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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatureThatRequiresMicrophonePermission() {
    var doNotShowRationale by rememberSaveable {
        mutableStateOf(false)
    }

    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.RECORD_AUDIO)

    val context = LocalContext.current

    PermissionRequired(
        permissionState = cameraPermissionState,
        permissionNotGrantedContent = {
            if (doNotShowRationale) {
                Text("Feature not available")
            } else {
                PermissionNotGrantedUI(
                    onYesClick = {
                        cameraPermissionState.launchPermissionRequest()
                    }, onCancelClick = {
                        doNotShowRationale = true
                    })
            }
        },
        permissionNotAvailableContent = {
            PermissionNotAvailableContent(
                onOpenSettingsClick = { /*context.openSettings()*/ })
        },
        content = {
            Text("Camera Permission Granted")
        }
    )


}

@Composable
fun PermissionNotAvailableContent(onOpenSettingsClick: () -> Unit) {

    Column {
        Text("Camera permission denied.")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onOpenSettingsClick() }) {
            Text("Open settings")
        }
    }
}


@Composable
fun PermissionNotGrantedUI(onYesClick: () -> Unit, onCancelClick: () -> Unit) {
    Column {
        Text("Camera is important for this app. Please grant the permission.")
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = {
                onYesClick()
            }) {
                Text("Yes")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                onCancelClick()
            }) {
                Text("Cancel")
            }
        }
    }

}