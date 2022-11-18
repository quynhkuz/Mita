package com.globits.mita.ui.assign.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.globits.mita.R
import com.globits.mita.data.model.Patient
import com.globits.mita.ui.nursing.view.LoadingItem
import com.globits.mita.ui.nursing.view.SetHeaderListPatient
import com.globits.mita.ui.nursing.view.SetLayoutItemPatient
import com.globits.mita.ui.nursing.view.SetUpToolbarLayoutLight
import com.globits.mita.ui.theme.*
import java.util.*
import java.util.Calendar.getInstance

@Preview
@Composable
fun DefaultListPatient() {

//    var listUser: LazyPagingItems<Patient>
//    SetLayoutListPatientFragmentAssign(onClickListener = {
//
//    }, onBackStack = {
//
//    }, listUser = listUser, getPatient = {}, valueState = remember {
//        mutableStateOf("Đang điều trị")
//    }
//    )
}

@Composable
fun SetLayoutListPatientFragmentAssign(
    listUser: LazyPagingItems<Patient>,
    onClickListener: (Patient) -> Unit,
    onBackStack: () -> Unit,
    getPatient: (filter: Int) -> Unit,
    valueState: MutableState<String>
) {

    var visible by remember { mutableStateOf(listUser == null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ) {
        SetUpToolbarLayoutLight(onBackStack = onBackStack)

        SetHeaderListPatient(valueState)
        {
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
            SetBodyListPatientAssign(listUser, onClickListener)
        }
    }
}


@Composable
fun SetBodyListPatientAssign(
    listUser: LazyPagingItems<Patient>,
    onClickListener: (Patient) -> Unit
) {

    var isLoadState by remember {
        mutableStateOf(true)
    }


    LazyColumn(content = {
        items(items = listUser)
        { patient ->
            patient?.let {
                SetLayoutItemPatient(patient = patient, Modifier.clickable {
                    onClickListener(patient)
                })
            }
        }


        when (listUser.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item {
                    AnimatedVisibility(visible = isLoadState) {
                        LoadingItem()
                    }
                }
            }

            is LoadState.Error -> {
                isLoadState = !isLoadState
            }
        }


        when (listUser.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        AnimatedVisibility(visible = isLoadState) {
                            CircularProgressIndicator(modifier = Modifier.padding(top = 100.dp))
                        }

                    }
                }
            }

            is LoadState.Error -> {
                isLoadState = !isLoadState
            }
        }
    })
}





@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .padding(8.dp),
            strokeWidth = 5.dp
        )

    }
}



@Composable
fun SetLayoutItemPatientAssign(patient: Patient, modifier: Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, top = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier
                .padding(16.dp)
        ) {
            SetLayoutPatientInfoItem(patient)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {
                Icon(
                    painter = painterResource(id = com.globits.mita.R.drawable.img_icon_file),
                    modifier = Modifier
                        .width(14.dp)
                        .height(14.dp)
                        .padding(top = 4.dp),
                    contentDescription = "",
                    tint = ICON_LOCATION

                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "#ACC:${patient.documents?.get(0)?.name}",
                    style = styleText
                )
            }
        }


    }
}

@Composable
fun SetLayoutPatientInfoItem(patient: Patient) {


    val cal: Calendar = getInstance()
    if (patient.dob != null) {
        cal.time = patient.dob
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.dp, color = Color.Transparent, RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
    ) {
        val (layoutImage, layoutText) = createRefs()
        Image(
            painterResource(id = R.drawable.img_patient), "Avatar",
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .constrainAs(layoutImage) {
                    start.linkTo(parent.start)
                }
                .fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .constrainAs(layoutText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = 52.dp)
                .fillMaxWidth()
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (name, btn) = createRefs()
                patient.displayName?.let {
                    Text(text = it, modifier = Modifier
                        .constrainAs(name) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                        .padding(end = 32.dp),
                        fontSize = 18.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_semi_bold))))
                }
                Image(
                    painterResource(id = R.drawable.img_three_dot), "Avatar",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .constrainAs(btn) {
                            end.linkTo(parent.end)
                        },
                )
            }
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (dob, status) = createRefs()
                Text(
                    text = "Sinh năm ${cal.get(Calendar.YEAR)} | ${
                        getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR)
                    } tuổi | ${if (patient.gender == "M") "Nam" else "Nữ"}",

//                    text = "Sinh năm 200",
                    modifier = Modifier.constrainAs(dob) {
                        start.linkTo(parent.start)
                    },
                    fontSize = 10.sp,
                    color = TEXT_DOB,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_regular)))
                )
                Text(
                    text = buildAnnotatedString {
                        append(if (patient.status == 1) "\u2022 Đang điều trị" else "\u2022 Kết thúc điều trị")

                    },
                    modifier = Modifier.constrainAs(status) {
                        start.linkTo(dob.end)
                        end.linkTo(parent.end)
                    },
                    fontSize = 10.sp,
                    color = if (patient.status == 1) TEXT_STATUS else ICON_LOCATION,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_regular)))
                )
            }
        }

    }
    Row(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.img_code_patient),
                modifier = Modifier
                    .width(13.dp)
                    .height(10.dp)
                    .clickable {
                        println("icon click")
                    }, contentDescription = "", tint = ICON_CODE_PATIENT

            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "Mã BN:${patient.code}",
                style = styleText
            )
        }
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.img_flag_patient),
                    modifier = Modifier
                        .width(13.dp)
                        .height(13.dp)
                        .clickable {
                            println("icon click")
                        }, contentDescription = "", tint = ICON_FLAG

                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "Đối tượng: ${if (patient.objectType == 0) "BHYT" else "Viện phí"}",
                    style = styleText
                )
            }
        }


    }
}

