package ch.mathieubroillet.jarvis.android.pages

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import ch.mathieubroillet.jarvis.android.R
import ch.mathieubroillet.jarvis.android.ui.theme.JarvisComposeTheme
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont
import ch.mathieubroillet.jarvis.android.utils.DefaultBox
import ch.mathieubroillet.jarvis.android.utils.requestPermission


@Composable
fun DisplayPermissionsPage(navController: NavController) {
    DefaultBox {
        PermissionsBase(navController)
    }
}

@Composable
fun PermissionsBase(navController: NavController) {
    Column(
        Modifier
            .padding(bottom = 25.dp)
            .fillMaxSize()
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
            stringResource(id = R.string.permissions_microphone),
            stringResource(id = R.string.permissions_microphone_description)
        )
    }
}

@Composable
fun PermissionRow(
    icon: Int = R.drawable.ic_baseline_error_24,
    title: String = "Name",
    description: String = "Description of the permission"
) {
    Row(
        Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "permission icon",
            modifier = Modifier.size(40.dp)
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
                fontSize = 14.sp
            )
        }
        requestPermission(permission = Manifest.permission.RECORD_AUDIO)
    }
}

@Preview(showBackground = true)
@Composable
fun PermissionsPagePreview() {
    JarvisComposeTheme {
        DisplayPermissionsPage(rememberNavController())
    }
}