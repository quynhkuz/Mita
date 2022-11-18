package com.globits.mita.ui.treatment.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.globits.mita.R
import com.globits.mita.data.model.Patient
import com.globits.mita.ui.nursing.view.SetLayoutItemPatient
import com.globits.mita.ui.theme.*
import java.util.*


@Preview
@Composable
fun DefaultPreviewPatientInfo() {
    SetLayoutPatientInfo("Thông tin bệnh nhân",
        false,
        onBackStack = { },
        onPatientClick = { }, patient = Patient())
}

@Composable
fun SetLayoutPatientInfo(
    textTitle: String,
    isNursing: Boolean,
    onPatientClick: (patient: Patient) -> Unit,
    onBackStack: () -> Unit,
    patient: Patient
) {
    Column(
        Modifier
            .background(Color.White)
    ) {
        SetUpToolbarLayout(textTitle, onBackStack)
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Box {
                Row(
                    Modifier
                        .background(BACKGROUND_TOOLBAR)
                        .fillMaxWidth()
                        .height(92.dp)
                ) {}
                Column(Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp)) {
                    SetLayoutItemPatient(
                        patient = patient,
                        Modifier
                    )
                }
            }
            // chuẩn đoán
            SetLayoutDiagnostic(patient)
            // danh sách chức năng
            SetUpListFeatureLayout(isNursing)
            {
                onPatientClick(patient)
            }
        }


    }
}

@Composable
fun SetLayoutDiagnostic(patient: Patient) {
    Column(Modifier.padding(start = 24.dp, end = 16.dp)) {
        Text(
            text = "Chuẩn đoán", fontSize = 17.sp,
            color = TEXT_BACK,
            style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = patient.diagnostic?:"",
            fontSize = 14.sp,
            color = TEXT_DOB,
            style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_regular)))
        )
    }
}

@Composable
fun SetUpListFeatureLayout(isNursing: Boolean, onPatientClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 12.dp)
            .fillMaxWidth()
    ) {
        SetUpItemFeatureLayout(title = "Tài liệu bệnh án"){}
        SetUpItemFeatureLayout(title = "Bệnh án", onPatientClick)
        SetUpItemFeatureLayout(title = "Ghi chú") {}
        if (isNursing) {
            SetUpItemFeatureLayout(title = "Tờ điều trị") {}
            SetUpItemFeatureLayout(title = "Chỉ định DVKT") {}
            SetUpItemFeatureLayout(title = "Kê đơn") {}
            SetUpItemFeatureLayout(title = "Công nợ") {}
            SetUpItemFeatureLayout(title = "PACS") {}
        } else {
            SetUpItemFeatureLayout(title = "Chăm sóc") {}
            SetUpItemFeatureLayout(title = "Chức năng sống") {}
            SetUpItemFeatureLayout(title = "Truyền dịch") {}
            SetUpItemFeatureLayout(title = "Thuốc, vật tư") {}
        }


    }
}

@Composable
fun SetUpItemFeatureLayout(title: String, onPatientClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, STROKE_BTN_FEATURE),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .clickable { onPatientClick() }
                .padding()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title, fontSize = 16.sp,
                color = PRIMARY_COLOR,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))
            )
            IconButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Menu Btn", tint = PRIMARY_COLOR,
                )
            }
        }
    }
}

@Composable
fun SetUpToolbarLayout(textTitle: String, onBackStack: () -> Unit) {

    var textTitle by remember {
        mutableStateOf(textTitle)
    }

    TopAppBar(
        title = {
            Text(
                text = textTitle,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            )
        },
        actions = {
            IconButton(
                onClick = { }, Modifier
                    .background(
                        shape = RoundedCornerShape(12.dp),
                        color = BACKGROUND_TOOLBAR
                    )
                    .height(44.dp)
                    .width(44.dp)
                    .border(
                        border = BorderStroke(1.dp, STROKE_COLOR),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.img_link),
                    contentDescription = "Menu Btn",
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackStack() }, Modifier
                    .background(
                        shape = RoundedCornerShape(12.dp),
                        color = BACKGROUND_TOOLBAR
                    )
                    .height(44.dp)
                    .width(44.dp)
                    .border(
                        border = BorderStroke(1.dp, STROKE_COLOR),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Menu Btn",
                )
            }
        },
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        elevation = 0.dp,
        modifier = Modifier
            .background(BACKGROUND_TOOLBAR)
            .padding(start = 16.dp, end = 16.dp)
    )
}

data class Doc(
    val name: String? = null,
    val count: Int? = null,
    val date: Date? = null
)



