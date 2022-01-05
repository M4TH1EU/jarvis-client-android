package ch.mathieubroillet.jarvis.android.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.mathieubroillet.jarvis.android.R
import ch.mathieubroillet.jarvis.android.ui.theme.JarvisComposeTheme
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont

@Composable
fun IconAlertDialogTextField(
    buttonIcon: Int = R.drawable.ic_baseline_error_24,
    title: String = "Title",
    label: String = "Label",
    text: String,
    textFieldValue: (String) -> Unit,
    onOKClick: () -> Unit
) {
    JarvisComposeTheme {
        Column {
            val openDialog = remember { mutableStateOf(false) }

            IconButton(onClick = { openDialog.value = true }) {
                Icon(
                    painter = painterResource(id = buttonIcon),
                    contentDescription = "icon button with dialog"
                )
            }

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog.value = false
                    },
                    title = null,
                    text = null,
                    buttons = {
                        Column {
                            Box(
                                modifier = Modifier
                                    .clip(RectangleShape)
                                    .background(color = MaterialTheme.colors.secondaryVariant)
                                    .padding(horizontal = 20.dp, vertical = 15.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = title,
                                    fontFamily = productSansFont,
                                    fontSize = 30.sp
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RectangleShape)
                                    .background(color = MaterialTheme.colors.background)
                                    .padding(horizontal = 20.dp)
                                    .padding(top = 15.dp, bottom = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    OutlinedTextField(
                                        value = text,
                                        onValueChange = textFieldValue,
                                        label = { Text(text = label) },
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                                            focusedLabelColor = MaterialTheme.colors.secondary
                                        )
                                    )

                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(top = 5.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(onClick = { openDialog.value = false }) {
                                            Text(
                                                text = stringResource(id = R.string.cancel),
                                                color = MaterialTheme.colors.secondary
                                            )
                                        }
                                        TextButton(onClick = {
                                            onOKClick(); openDialog.value = false
                                        }) {
                                            Text(
                                                text = stringResource(id = R.string.ok),
                                                color = MaterialTheme.colors.secondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }

    }
}