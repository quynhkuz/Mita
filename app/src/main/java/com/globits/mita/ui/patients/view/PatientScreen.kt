package com.globits.mita.ui.patients

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.globits.mita.R
import com.globits.mita.data.model.labtest.LabTest
import com.globits.mita.data.model.labtest.LabTestItem
import com.globits.mita.data.model.labtest.LabTestItemDetait
import com.globits.mita.data.model.labtestxray.LabTestXRayItemDetail
import com.globits.mita.data.model.labtestxray.LabTestXRay
import com.globits.mita.data.model.labtestxray.LabTestXRayItem
import com.globits.mita.data.model.prescriptions.Medicine
import com.globits.mita.data.model.prescriptions.PresCripTion
import com.globits.mita.ui.assign.view.SetLine
import com.globits.mita.ui.theme.*
import com.globits.mita.ui.treatment.view.SetUpToolbarLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Preview
@Composable
fun PreviewLayout() {
    SetLayoutPatientActivity(mutableListOf(), mutableListOf(), mutableListOf()) {

    }
}

@Composable
fun SetLayoutPatientActivity(
    listLabTest: List<LabTest>,
    listLabTestXRay: List<LabTestXRay>,
    listPrescription: List<PresCripTion>,
    onBackStack: () -> Unit
) {
    MaterialTheme() {
        Column() {
            Column(modifier = Modifier.background(PRIMARY_COLOR)) {
                SetUpToolbarLayout("Bệnh án", onBackStack)
                SetViewPagerLayout(
                    listLabTest,
                    listLabTestXRay,
                    listPrescription
                )
            }
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetViewPagerLayout(
    listLabTest: List<LabTest>,
    listLabTestXRay: List<LabTestXRay>,
    listPrescription: List<PresCripTion>
) {
    val tabData = listOf(
        "Xét nghiệm", "CĐHA", "Thuốc"
    )
    val pagerState = rememberPagerState(
        pageCount = tabData.size,
        initialOffscreenLimit = 3,
        infiniteLoop = false,
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    // TAB
    TabRow(
        selectedTabIndex = tabIndex,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(20.dp)
            .border(
                width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp)
            ), indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.customTabIndicatorOffset(it[tabIndex]),
                height = 0.dp,
                color = Color.Transparent
            )
        }, backgroundColor = Color.Transparent, contentColor = Color.Transparent
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(
                selected = tabIndex == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = text,
                        color = if (tabIndex == index) PRIMARY_COLOR else Color.White,
                    )
                },
                modifier = Modifier
                    .clip(
                        shape = when (index) {
                            0 -> RoundedCornerShape(
                                topStart = 10.dp,
                                bottomStart = 10.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                            2 -> RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 0.dp,
                                topEnd = 10.dp,
                                bottomEnd = 10.dp
                            )
                            else -> RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 0.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                        }
                    )
                    .background(if (tabIndex == index) Color.White else PRIMARY_COLOR)
                    .border(
                        width = 0.5.dp,
                        Color.White,

                        )
                    .drawBehind {
                        drawLine(
                            Color.Blue,
                            Offset(size.width, 0f),
                            Offset(size.width, size.height),
                            4f
                        )
                    },
                selectedContentColor = Color.Transparent,
                unselectedContentColor = Color.Transparent,
            )
        }
    }
    HorizontalPager(
        state = pagerState, modifier = Modifier.background(Color.White)
    ) { index ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (tabData[index]) {
                tabData[0] -> SetPatientSession0(listLabTest)
                tabData[1] -> SetPatientSession1(listLabTestXRay)
                tabData[2] -> SetPatientSession2(listPrescription)
            }
        }
    }
}


fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val indicatorWidth = 32.dp
    val currentTabWidth = currentTabPosition.width
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left + currentTabWidth / 2 - indicatorWidth / 2,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(indicatorWidth)
}

@Composable
fun SetPatientSession0(listLabTest: List<LabTest>) {
    listLabTest.forEach { item ->
        SetItemPatient(
            item.dateSpecified,
            item.heathOrganization,
            item.doctorSpecified,
            content = {
                item.labTestItems?.forEach { item ->
                    ContentLabTest(item)
                }
            })
    }
}


@Composable
fun SetPatientSession1(listLabTestXRay: List<LabTestXRay>) {

    listLabTestXRay.forEach { item ->

        SetItemPatient(
            item.dateSpecified,
            item.heathOrganization,
            item.doctorSpecified,
            content = {
                item.labTestXRayItem?.forEach {
                    ContentCDHA(it)
                }
            })

    }
}


@Composable
fun SetPatientSession2(listPrescription: List<PresCripTion>) {

    listPrescription.forEach { item ->

        SetItemPatient(
            item.dateSpecified,
            item.heathOrganization,
            item.doctorSpecified,
            content = {

                //
                item.medicines?.forEach {
                    ContentMedicine(it)
                }
            })
    }

}

@Composable
fun SetItemPatient(
    dateSpecified: Date?,
    heathOrigination: String?,
    doctorSpecified: String?,
    content: @Composable () -> Unit
) {

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val formattedDate: String = dateFormat.format(dateSpecified).toString()

    Card(
        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp, start = 20.dp, end = 20.dp),
        border = BorderStroke(color = PATIENT_BACKGROUND, width = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PATIENT_BACKGROUND)
            ) {
                Text(
                    text = "Ngày chỉ định: ${formattedDate}", fontSize = 16.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_semi_bold))),
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp
                        )
                        .fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Khoa/Phòng chỉ định",
                        style = styleText4
                    )
                    Text(
                        text = "Người chỉ định",
                        style = styleText4
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = heathOrigination ?: "Không có dữ liệu",
                        style = styleText2
                    )
                    Text(
                        text = doctorSpecified ?: "Không có dữ liệu",
                        style = styleText2
                    )
                }

                var visible by remember {
                    mutableStateOf(false)
                }

                this@Column.AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(
                        // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 250)
                    )
                ) {
                    // Content that needs to appear/disappear goes here:
                    Column() {

                        content()
                        SetLine()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                                .clickable {
                                    visible = false
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Thu gọn",
                                style = styleText1,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                            Icon(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(10.dp),
                                painter = painterResource(id = R.drawable.img_icon_hidden),
                                contentDescription = "icon", tint = PRIMARY_COLOR
                            )
                        }
                    }

                }
                this@Column.AnimatedVisibility(
                    visible = !visible,
                    enter = fadeIn(
                        // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 250)
                    )
                ) {
                    // Content that needs to appear/disappear goes here:
                    SetLine()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .clickable {
                                visible = true
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Chi tiết",
                            style = styleText1,
                            modifier = Modifier.padding(end = 10.dp),
                            fontSize = 14.sp
                        )
                        Icon(
                            modifier = Modifier
                                .width(10.dp)
                                .height(10.dp),
                            painter = painterResource(id = R.drawable.img_icon_show),
                            contentDescription = "icon", tint = PRIMARY_COLOR
                        )
                    }
                }
            }

        }

    }

}

@Composable
fun SetLayoutTitle(text: String) {
    Row(
        modifier = Modifier
            .background(BACKGROUND_TEXT)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text.uppercase(),
            style = styleText3,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
        )
    }
}


@Composable
fun ContentLabTest(labTestItem: LabTestItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SetLayoutTitle(labTestItem.name ?: "Không có dữ liệu hiển thị")
        SetLayoutResultLabTest(
            labTestItem.dateResult,
            labTestItem.doctorResult
        )
        Text(
            text = "Tổng phân tích tế bào máu ngoại vi",
            fontSize = 14.sp,
            style = styleText4,

            )
        Text(
            text = "(Bằng máy đếm tổng trở)",
            style = styleText2, fontSize = 14.sp,
            color = PRIMARY_COLOR
        )
    }
    labTestItem.labTestItemDetails?.forEach {
        SetItemLabTest(it)
    }

}

@Composable
fun SetLayoutResultLabTest(
    dateResult: Date?,
    doctorResult: String?
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val formattedDate: String = dateFormat.format(dateResult).toString()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 2.dp),
            text = "Ngày trả KQ",
            style = styleText4
        )
        Text(
            text = "Người trả KQ",
            style = styleText4
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedDate,
            style = styleText2
        )
        Text(
            text = doctorResult ?: "Không có dữ liệu hiển thị",
            style = styleText2
        )
    }
}

@Composable
fun ContentCDHA(labTestXRayItem: LabTestXRayItem) {

    SetLayoutTitle(text = labTestXRayItem.name?: "Không có dữ liệu hiển thị")
    SetLayoutResultLabTest(
        labTestXRayItem.dateResult,
        labTestXRayItem.doctorResult
    )
    labTestXRayItem.labTestXRayItemDetails?.forEach {
        SetItemCDHD(it)
    }

}

@Composable
fun ContentMedicine(medicine: Medicine) {
    SetItemMedicine(medicine)
}

@Composable
fun SetItemMedicine(medicine: Medicine) {
    Card(
        modifier = Modifier.padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding()
                .background(BACKGROUND_ITEM_LAB_TEST)
                .padding(16.dp)
        ) {
            Text(
                text = medicine.name?:"Không có dữ liệu hiển thị",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                fontStyle = FontStyle(R.font.nunito_sans_semi_bold)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Số lượng",
                    style = styleText4
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text =  medicine.amount.toString(),
                    style = styleText2,
                )

            }
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp),
                text = "HDSD",
                style = styleText4
            )
            Text(
                text = medicine.userManual?:"",
                style = styleText2
            )
        }
    }
}

@Composable
fun SetItemLabTest(labTestItemDetait: LabTestItemDetait) {

    val resulNumber = labTestItemDetait.resulNumber!!.toFloat()
    val referenceNumberMin = labTestItemDetait.labTestItemDetailTemplate?.referenceNumberMin!!.toFloat()
    val referenceNumberMax = labTestItemDetait.labTestItemDetailTemplate?.referenceNumberMax!!.toFloat()

    Card(
        modifier = Modifier.padding(top = 12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding()
                .background(BACKGROUND_ITEM_LAB_TEST)
                .padding(16.dp)
        ) {
            Text(
                text = labTestItemDetait.labTestItemDetailTemplate?.name
                    ?: "Không có  dữ liệu hiển thị",
                color = if(resulNumber < referenceNumberMin || resulNumber > referenceNumberMax) TEXT_RED else TEXT_COLOR4,
                fontSize = 16.sp,
                fontStyle = FontStyle(R.font.nunito_sans_semi_bold)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Kết quả",
                    style = styleText4
                )
                Text(
                    text = "Giá trị tham chiếu",
                    style = styleText4
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${labTestItemDetait.resulNumber} g/l",
                    fontWeight = FontWeight.Bold,
                    style = if(resulNumber < referenceNumberMin || resulNumber > referenceNumberMax) styleText5 else styleText2
                )
                Text(
                    text = "${labTestItemDetait.labTestItemDetailTemplate?.referenceNumberMin} - ${labTestItemDetait.labTestItemDetailTemplate?.referenceNumberMax}",
                    fontWeight = FontWeight.Bold,
                    style = if(resulNumber < referenceNumberMin || resulNumber > referenceNumberMax) styleText5 else styleText2
                )
            }

        }
    }

}

@Composable
fun SetItemCDHD(labTestXRayItemDetail: LabTestXRayItemDetail) {
    Card(
        modifier = Modifier.padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding()
                .background(BACKGROUND_ITEM_LAB_TEST)
                .padding(16.dp)
        ) {
            Text(
                text = labTestXRayItemDetail.name?:"Không có dữ liệu hiển thị",
                color = TEXT_COLOR4,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle(R.font.nunito_sans_semi_bold)
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp),
                text = "Kết luận",
                style = styleText4
            )
            Text(
                text = labTestXRayItemDetail.conclusion?:"Không có dữ liệu hiện thị ",
                style = styleText2
            )
        }
    }
}




