package com.globits.mita.ui.assign.view

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.globits.mita.data.model.labtestassign.LabTestAssign
import com.globits.mita.data.model.labtestassign.LabTestAssignTemplate
import com.globits.mita.ui.theme.BACKGROUND_ITEM_LAB_TEST
import com.globits.mita.ui.theme.PRIMARY_COLOR
import com.globits.mita.ui.theme.STROKE_COLOR
import com.globits.mita.ui.theme.STROKE_COLOR_EDIT_TEXT
import com.globits.mita.utils.formatNumber
import ir.kaaveh.sdpcompose.sdp
import kotlin.math.min


@Preview
@Composable
fun DefaultDiaLog() {
    var listLabTest = remember {
        mutableStateListOf(LabTestAssignTemplate())
    }

    var openDialog by remember { mutableStateOf(true) }
    CustomDialogScrollable(onDismiss = { openDialog },
        onConfirmClicked = {}, listLabTestTemplate = listLabTest)
}



@Composable
fun CustomDialogScrollable(
    onConfirmClicked: (listLabTestAssignTemplate: SnapshotStateList<LabTestAssignTemplate>) -> Unit,
    onDismiss: () -> Unit,
    listLabTestTemplate : SnapshotStateList<LabTestAssignTemplate>
) {

    val listLabTestAssignTemplate = remember {
        mutableStateListOf<LabTestAssignTemplate>()
    }



    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = Modifier
                .padding(top = 24.sdp, bottom = 24.sdp)
                .border(width = 0.dp, color = Color.Transparent, shape = RoundedCornerShape(15.dp))
                .clip(shape = RoundedCornerShape(15.dp)),
            color = MaterialTheme.colors.surface,
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f, fill = false)
                        .padding(vertical = 16.dp)
                ) {

                    Text(
                        text = "Danh Sách Chỉ Định",
                        color = PRIMARY_COLOR,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    AnimatedVisibility(
                        visible = listLabTestTemplate.size == 0
                    ) {
                        Text(
                            "Không có phiếu chỉ định",
                            modifier = Modifier.fillMaxWidth()
                                .height(100.sdp)
                                .padding(top = 45.sdp),
                            color = STROKE_COLOR_EDIT_TEXT,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    AnimatedVisibility(
                        visible = listLabTestTemplate.size != 0
                    ){
                        LazyColumn(modifier = Modifier.defaultMinSize(minHeight = 150.sdp), content = {
                            items(listLabTestTemplate)
                            {
                                ItemAssign(labTestAssignTemplate = it,listLabTestAssignTemplate)
                            }
                        })
                    }




                }

                // BUTTONS
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel",
                            color = PRIMARY_COLOR)
                    }
                    TextButton(onClick = { onConfirmClicked(listLabTestAssignTemplate) }) {
                        Text(text = "OK",
                            color = PRIMARY_COLOR)
                    }
                }
            }
        }
    }
}



@Composable
fun ItemAssign(
    labTestAssignTemplate: LabTestAssignTemplate,
    listLabTestAssignTemplate: SnapshotStateList<LabTestAssignTemplate>
) {
    var isCheck by remember {
        mutableStateOf(false)
    }

    Row(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .border(width = 1.dp, color = STROKE_COLOR_EDIT_TEXT, shape = RoundedCornerShape(8.dp))
        .clip(shape = RoundedCornerShape(8.dp))
        .background(color = if (isCheck) BACKGROUND_ITEM_LAB_TEST else Color.Transparent)
        .defaultMinSize(minHeight = 60.dp)
        .clickable {
            isCheck = !isCheck
            if (isCheck) {
                listLabTestAssignTemplate.add(labTestAssignTemplate)
            } else {
                listLabTestAssignTemplate.remove(labTestAssignTemplate)
            }
        }
        .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            modifier =  Modifier.fillMaxWidth(0.7f),
            text = labTestAssignTemplate.name.toString(),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            textAlign = TextAlign.End,
            maxLines = 1,
            text = formatNumber(labTestAssignTemplate.price!!.toInt()),
            fontWeight = FontWeight.SemiBold
        )

    }
    Spacer(modifier = Modifier.height(8.dp))
}
