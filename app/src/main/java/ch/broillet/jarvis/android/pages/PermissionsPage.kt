package ch.broillet.jarvis.android.pages

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.broillet.jarvis.android.R
import ch.broillet.jarvis.android.ui.theme.JarvisclientappTheme
import ch.broillet.jarvis.android.ui.theme.productSansFont
import ch.broillet.jarvis.android.utils.DefaultBox
import ch.broillet.jarvis.android.utils.openAppSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun DisplayPermissionsPage(navController: NavController) {
    DefaultBox {
        PermissionsBase()
    }
}

@Composable
fun PermissionsBase() {
    Column(
        Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth()
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shield_24),
                contentDescription = "shield icon",
                modifier = Modifier.size(50.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.permissions_page_permissions_page_before_starting),
            fontFamily = productSansFont,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 15.dp)
        )
        Text(
            text = stringResource(id = R.string.permissions_page_this_app_need_the_following_permissions),
            fontFamily = productSansFont,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp)
        )

        // List of required permissions to display
        PermissionRow(
            R.drawable.ic_baseline_mic_24,
            stringResource(id = R.string.permission_microphone),
            stringResource(id = R.string.permission_microphone_description),
            Manifest.permission.RECORD_AUDIO
        )
    }
}

@Composable
fun PermissionRow(
    icon: Int = R.drawable.ic_baseline_error_24,
    title: String = "Name",
    description: String = "Description of the permission",
    permission: String
) {
    Row(
        Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.75F)) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "permission icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )
            Column {
                Text(
                    text = title,
                    fontFamily = productSansFont,
                    fontSize = 18.sp
                )
                Text(
                    text = description,
                    fontFamily = productSansFont,
                    fontSize = 14.sp,
                )
            }
        }

        // Remove preview error (only in IDE, has no impact on app)
        if (!LocalInspectionMode.current) {
            PermissionRequestButton(permission = permission)
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequestButton(permission: String) {
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
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.error)
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

@Preview(showBackground = true)
@Composable
fun PermissionsPagePreview() {
    JarvisclientappTheme {
        DisplayPermissionsPage(rememberNavController())
    }
}