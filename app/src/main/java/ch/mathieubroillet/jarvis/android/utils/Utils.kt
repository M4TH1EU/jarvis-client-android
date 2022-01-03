package ch.mathieubroillet.jarvis.android.utils

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DefaultBox(
    content: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 45.dp, bottom = 10.dp)
    ) {
        content()
    }

}