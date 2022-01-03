package ch.mathieubroillet.jarvis.android.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ch.mathieubroillet.jarvis.android.R
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont


@Composable
fun MessageFromJarvis(text: String) {
    //We create a row to contain the message and the robot image (to look like an sms)
    Row(Modifier.padding(bottom = 25.dp)) {

        // Adding the robot image as the sender
        Image(
            painter = painterResource(id = R.drawable.robot256),
            contentDescription = "robot",
            Modifier
                .size(50.dp)
                .padding(end = 10.dp)
        )

        // Adding the message box with the text given in the params
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
    //We create a row to contain the user message and we align the row to the right side (to look like a conversation between two people)
    Row(
        Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.End
    ) {
        // The message box with the text
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
                color = if (!isSystemInDarkTheme()) Color.White else Color(15, 15, 15, 255)
            )
        }
    }
}
