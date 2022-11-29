package ch.broillet.jarvis.android.pages

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import ch.broillet.jarvis.android.utils.requestPermissionButton

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

        PermissionRow(
            R.drawable.ic_baseline_mic_24,
            stringResource(id = R.string.permission_microphone),
            stringResource(id = R.string.permission_microphone_description),
            Manifest.permission.RECORD_AUDIO
        )

        /*PermissionRow(
            R.drawable.ic_baseline_folder_open_24,
            stringResource(id = R.string.permission_files),
            stringResource(id = R.string.permission_files_description),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )*/
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

        requestPermissionButton(permission = permission)
    }
}

@Preview(showBackground = true)
@Composable
fun PermissionsPagePreview() {
    JarvisclientappTheme {
        DisplayPermissionsPage(rememberNavController())
    }
}