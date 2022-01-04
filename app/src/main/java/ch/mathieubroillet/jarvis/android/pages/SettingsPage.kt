package ch.mathieubroillet.jarvis.android.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.mathieubroillet.jarvis.android.R
import ch.mathieubroillet.jarvis.android.nav.Screen
import ch.mathieubroillet.jarvis.android.ui.theme.JarvisComposeTheme
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont
import ch.mathieubroillet.jarvis.android.utils.DefaultBox


@Composable
fun DisplaySettingsPage(navController: NavController) {
    DefaultBox {
        SettingsBase(navController)
    }
}

@Composable
fun SettingsBase(navController: NavController) {
    Column(Modifier.padding(bottom = 25.dp)) {
        Row {
            IconButton(onClick = { navController.navigate(Screen.MainScreen.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "back button"
                )
            }
        }
        Text(
            text = stringResource(id = R.string.settings),
            fontFamily = productSansFont,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreview() {
    JarvisComposeTheme {
        DisplaySettingsPage(rememberNavController())
    }
}