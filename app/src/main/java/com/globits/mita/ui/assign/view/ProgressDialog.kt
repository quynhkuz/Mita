package com.globits.mita.ui.assign.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.globits.mita.ui.theme.PRIMARY_COLOR


@Preview
@Composable
fun DefaultProgessDiaLog() {
    ProgressDialog()

}


@Composable
fun ProgressDialog() {

    Dialog(
        onDismissRequest = {false},
        DialogProperties(
            dismissOnClickOutside = false
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = PRIMARY_COLOR,
                    modifier = Modifier.then(Modifier.size(50.dp)),
                    strokeWidth = Dp(5F)
                )
            }
        }
    }
}