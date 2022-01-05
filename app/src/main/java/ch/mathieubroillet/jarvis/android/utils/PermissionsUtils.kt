package ch.mathieubroillet.jarvis.android.utils

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ch.mathieubroillet.jarvis.android.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun requestPermissionButton(permission: String) {
    val permissionState = rememberPermissionState(permission = permission)
    val context = LocalContext.current

    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            Button(onClick = {
                permissionState.launchPermissionRequest()
            }) {
                Text(text = stringResource(id = R.string.permissions_page_grant_permission))
            }

        },
        permissionNotAvailableContent = {
            Button(
                onClick = { openAppSettings(context) },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Text(text = stringResource(id = R.string.permissions_page_permission_denied))
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_settings_24),
                    contentDescription = "app settings icon"
                )
            }
        },
        content = {
            Button(onClick = {}, enabled = false) {
                Text(text = stringResource(id = R.string.permission_granted))
            }
        }
    )
}