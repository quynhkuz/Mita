package com.globits.mita.ui.nursing.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.globits.mita.data.model.Patient

@Preview
@Composable
fun DefaultPreviewNursingPatient() {
//    SetLayoutListPatientFragment(onBackStack = {}, onClickListener = {}, getPatient = {})


}

@Composable
fun SetLayoutListPatientFragment(
    onClickListener: (Patient) -> Unit,
    onBackStack: () -> Unit,
    getPatient: (filter: Int) -> Unit,
    items: LazyPagingItems<Patient>,
    valueState: MutableState<String>
) {

    var visible by remember { mutableStateOf(items == null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ) {
        SetUpToolbarLayoutLight(onBackStack = onBackStack)
        SetHeaderListPatient(valueState = valueState) {
            getPatient(it)
        }

        AnimatedVisibility(
            visible = visible
        ) {
            Text(
                "Không có dữ liệu bệnh nhân",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        AnimatedVisibility(
            visible = !visible
        ) {
            SetBodyListPatient(onClickListener, items)
        }


    }
}

