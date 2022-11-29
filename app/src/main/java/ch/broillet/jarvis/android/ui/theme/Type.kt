package ch.broillet.jarvis.android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ch.broillet.jarvis.android.R

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
    bodyLarge = TextStyle(
        fontFamily = productSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)