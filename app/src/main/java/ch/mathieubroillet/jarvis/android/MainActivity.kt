package ch.mathieubroillet.jarvis.android

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import ch.mathieubroillet.jarvis.android.ui.theme.JarvisComposeTheme
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            // Fix the status & navigation bars staying white when white theme is on with activity in fullscreen
            val wic = WindowInsetsControllerCompat(window, window.decorView)
            wic.isAppearanceLightStatusBars = !isSystemInDarkTheme()
            wic.isAppearanceLightNavigationBars = !isSystemInDarkTheme()

            JarvisComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    //TODO: check if this is okay???
                    DefaultPreview()
                }
            }
        }
    }
}


//Draws the base of the main activity, that includes the 3-dots menu and the "hi text".
@Composable
fun Base() {
    Column(Modifier.padding(bottom = 25.dp)) {
        Row(Modifier.align(Alignment.End)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                    contentDescription = "3 dots button"
                )
            }
        }

        Text(
            text = "Bonjour, comment puis-je vous aider ?",
            fontFamily = productSansFont,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 50.dp)
        )
    }
}

@Composable
fun MessageFromJarvis(text: String) {
    Row(Modifier.padding(bottom = 25.dp)) {
        Image(
            painter = painterResource(id = R.drawable.robot256),
            contentDescription = "robot",
            Modifier
                .size(50.dp)
                .padding(end = 10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9F)
                .clip(RoundedCornerShape(15.dp))
                .background(color = MaterialTheme.colors.secondaryVariant)
                .padding(horizontal = 10.dp, vertical = 5.dp)

        ) {
            Text(text = text, fontFamily = productSansFont)
        }
    }
}

@Composable
fun MessageFromUser(text: String) {
    Row(
        Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8F)
                .clip(RoundedCornerShape(15.dp))
                .background(color = MaterialTheme.colors.secondary)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                text = text,
                fontFamily = productSansFont,
                color = if (!isSystemInDarkTheme()) Color.White else Color(15, 15, 15, 255))
        }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JarvisComposeTheme {
        Column(Modifier.padding(top = 40.dp, start = 20.dp, end = 20.dp)) {
            Base()
            MessageFromJarvis(text = "Salut, je suis Jarvis! \nPose moi une question et je ferais de mon mieux pour te renseigner.")
            MessageFromUser(text = "Quel temps fait-il à Paris en ce moment ?")
            MessageFromJarvis(text = "A Paris, il fait actuellement 10 degrés et le ciel est nuageux.")

        }
    }
}