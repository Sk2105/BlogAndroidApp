package com.composeapp.blogapp.presentation.blog.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlin.reflect.KProperty


@Composable
fun DeleteDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    var isDeleting by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            onCancel()

        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xff212121))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Are you sure you want to delete this blog?",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {


                OutlinedButton(
                    onClick = {
                        onCancel()
                    },
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, color = Color(0xfffc107)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White,

                        )
                ) {

                    Text(
                        text = "Cancel",
                        color = Color.White
                    )

                }
                Spacer(
                    modifier = Modifier
                        .padding(8.dp)

                )

                Button(
                    onClick = {
                        onConfirm()
                        isDeleting = true
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffffc107),
                        contentColor = Color.White
                    )
                )
                {
                    if (isDeleting) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(20.dp)
                                .clip(RoundedCornerShape(50)),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "Delete",
                            color = Color.White
                        )
                    }

                }


            }

        }

    }
}