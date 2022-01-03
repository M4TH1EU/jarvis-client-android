package ch.mathieubroillet.jarvis.android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ch.mathieubroillet.jarvis.android.R

// Adding the Product Sans fonts
val productSansFont = FontFamily(
    Font(R.font.google_regular),
    Font(R.font.google_thin, weight = FontWeight.Thin),
    Font(R.font.google_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.google_light, weight = FontWeight.Light),
    Font(R.font.google_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.google_medium, weight = FontWeight.Medium),
    Font(R.font.google_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.google_bold, weight = FontWeight.Bold),
    Font(R.font.google_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.google_black, weight = FontWeight.Black),
    Font(R.font.google_black_italic, weight = FontWeight.Black, style = FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = productSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = productSansFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = productSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    defaultFontFamily = productSansFont
)

