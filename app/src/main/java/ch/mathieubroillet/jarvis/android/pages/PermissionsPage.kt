package ch.mathieubroillet.jarvis.android.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.mathieubroillet.jarvis.android.R
import ch.mathieubroillet.jarvis.android.ui.theme.JarvisComposeTheme
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont
import ch.mathieubroillet.jarvis.android.utils.DefaultBox


@Composable
fun DisplayPermissionsPage(navController: NavController) {
    DefaultBox {
        PermissionsBase(navController)
    }
}

@Composable
fun PermissionsBase(navController: NavController) {
    Column(Modifier.padding(bottom = 25.dp)) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shield_24),
                contentDescription = "shield icon",
                modifier = Modifier.size(50.dp)
            )
        }
        Text(
            text = "Avant de commencer",
            fontFamily = productSansFont,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 15.dp)
        )
        Text(
            text = "Cette application a besoin des autorisations suivantes pour fonctionner :",
            fontFamily = productSansFont,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp)
        )

        PermissionRow(R.drawable.ic_baseline_mic_24, "Microphone", "Nécessaire pour vous entendre.")
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
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Autoriser")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PermissionsPagePreview() {
    JarvisComposeTheme {
        DisplayPermissionsPage(rememberNavController())
    }
}