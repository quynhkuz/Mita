package com.globits.mita.ui.home

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.globits.mita.R
import com.globits.mita.data.model.User
import com.globits.mita.ui.theme.*
import ir.kaaveh.sdpcompose.sdp
import java.util.*

@Preview
@Composable
fun DefaultPreview() {
    var user: MutableState<User> = remember {
        mutableStateOf(User())
    }
    setLayoutFragment(
        requireContext = null,
        onClickListNursing = {},
        onClickListAssign = {},
        onClickListPacs = {},
        onClickListTreatment = {},
        user = user
    )
}

@Composable
fun setLayoutFragment(
    requireContext: Context?,
    onClickListNursing: () -> Unit,
    onClickListTreatment: () -> Unit,
    onClickListPacs: () -> Unit,
    onClickListAssign: () -> Unit,
    user: MutableState<User>
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            setLayoutHeader(user = user)
            setLayoutFeature(
                onClickListNursing,
                onClickListTreatment,
                onClickListPacs,
                onClickListAssign
            )
            setLayoutNextAction()
        }
    }

}

@Composable
fun setLayoutHeader(
    user: MutableState<User>
) {
    val nameRemember by remember { mutableStateOf("Trang") }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "\uD83D\uDC4B Xin chào!",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Bác sĩ ${user.value.displayName}",
                    fontSize = 28.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(), horizontalAlignment = Alignment.End
            ) {
                Box(contentAlignment = Alignment.TopEnd) {
                    var visible by remember {
                        mutableStateOf(true)
                    }
                    Image(
                        painterResource(id = R.drawable.img_avt), "Avatar",
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .width(48.dp)
                            .height(48.dp),
                    )
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
                        Image(
                            painterResource(id = R.drawable.img_dot_red), "Dot",
                            modifier = Modifier
                                .width(16.dp)
                                .height(16.dp),
                        )
                    }

                }
            }

        }
        SetLayoutSearch("Tìm kiếm") {

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetLayoutSearch(title: String, onClickSearch: () -> Unit) {

    var keyboardController = LocalSoftwareKeyboardController.current

    var textSearch by remember {
        mutableStateOf("")
    }
    val iconStart: @Composable (() -> Unit) = {
        Icon(
            painter = painterResource(id = R.drawable.img_search),
            modifier = Modifier
                .width(18.dp)
                .height(18.dp), contentDescription = "", tint = COLOR_TEXT
        )
    }
    val iconEnd: @Composable (() -> Unit) =
        {
            Icon(
                painter = painterResource(id = R.drawable.img_filter),
                modifier = Modifier
                    .width(18.sdp)
                    .height(18.dp)
                    .clickable {
                        onClickSearch()
                    }, contentDescription = "", tint = COLOR_TEXT
            )
        }
    TextField(
        value = textSearch,
        placeholder = { Text(text = title, color = COLOR_TEXT) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        onValueChange = { textSearch = it },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = BACKGROUND_EDIT_TEXT
        ),
        shape = RoundedCornerShape(16.dp),
        leadingIcon = iconStart,
        trailingIcon = iconEnd,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            },
        )
    )
}

@Composable
fun setLayoutFeature(
    onClickListNursing: () -> Unit,
    onClickListTreatment: () -> Unit,
    onClickListPacs: () -> Unit,
    onClickListAssign: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()

    ) {
        Text(
            text = "Tính năng",
            fontSize = 18.sp,
            style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))
        )
        SetLayoutItemFeature(
            title1 = "Điều dưỡng",
            title2 = "Điều trị",
            image1 = R.drawable.img_treatment,
            image2 = R.drawable.img_nursing,
            onClickListener1 = { onClickListNursing() }) {
            onClickListTreatment()
        }
        SetLayoutItemFeature(
            title1 = "PACS",
            title2 = "Kí số",
            image1 = R.drawable.img_pacs,
            image2 = R.drawable.img_sign_number,
            onClickListener1 = { onClickListPacs() }) {

        }
        SetLayoutItemFeature(
            title1 = "Chỉ định",
            title2 = "Ghi chú",
            image1 = R.drawable.img_specifi,
            image2 = R.drawable.img_note,
            onClickListener1 = { onClickListAssign() }) {

        }

    }
}

@Composable
fun setLayoutNextAction() {
    var listState by remember {
        mutableStateOf(
            mutableListOf<Daily>(
                Daily(
                    Calendar.getInstance(),
                    "Dr. Mim Akhter",
                    "Depression"
                ), Daily(
                    Calendar.getInstance(),
                    "Dr. Mim Akhter",
                    "Depression"
                ), Daily(
                    Calendar.getInstance(),
                    "Dr. Mim Akhter",
                    "Depression"
                )
            )
        )
    }
    Column(modifier = Modifier.padding(top = 30.dp)) {
        Text(
            text = "Hoạt động sắp tới",
            fontSize = 18.sp,
            style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))
        )
        LazyRow(modifier = Modifier.padding(top = 12.dp)) {
            items(listState) { item ->
                SetLayoutItemNextAction(item)
            }
        }
    }
}

@Composable
fun SetLayoutItemFeature(
    title1: String,
    title2: String,
    image1: Int,
    image2: Int,
    onClickListener1: () -> Unit,
    onClickListener2: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        val (layout1, layout2) = createRefs()
        Card(modifier = Modifier
            .constrainAs(layout1) {
                start.linkTo(parent.start)
            }
            .padding(end = 6.dp)
            .padding(end = 8.dp)
            .fillMaxWidth(.5f), elevation = 4.dp, shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.clickable {
                onClickListener1()
            }) {
                Image(
                    painterResource(id = image1), "Avatar",
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .padding(4.dp),
                )
                Text(
                    text = title1, modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))

                )
            }
        }

        Card(
            modifier = Modifier
                .constrainAs(layout2) {
                    start.linkTo(layout1.end)
                    end.linkTo(parent.end)
                }
                .padding(start = 6.dp)
                .padding(end = 8.dp)
                .fillMaxWidth(.5f), elevation = 4.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.clickable {
                onClickListener2()
            }) {
                Image(
                    painterResource(id = image2), "Avatar",
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .padding(4.dp),
                )
                Text(
                    text = title2,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))

                )
            }

        }
    }

}

@Composable
fun SetLayoutItemNextAction(item: Daily) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .width(275.dp)
                .height(120.dp)
                .padding(end = 16.dp), shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(BACKGROUND_DAILY)
                    .padding(end = 16.dp, start = 10.dp)
            ) {
                Card(
                    modifier = Modifier
                        .height(100.dp)
                        .width(70.dp)
                        .align(Alignment.CenterVertically), shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(BACKGROUND_DATE),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tháng ${item.time.get(Calendar.MONTH)}",
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp, color = Color.White,
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))
                        )
                        Text(
                            modifier = Modifier.padding(top = 2.dp),
                            text = "${item.time.get(Calendar.DAY_OF_MONTH)}",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp, color = Color.White,
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_extra_bold)))
                        )
                        Text(
                            modifier = Modifier.padding(top = 2.dp),
                            text = if (item.time.get(Calendar.DAY_OF_WEEK) == 1) "Chủ nhật" else "Thứ ${
                                item.time.get(
                                    Calendar.DAY_OF_WEEK
                                )
                            }",
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp, color = Color.White,
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_regular)))
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(
                            start = 24.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.img_three_dot),
                            modifier = Modifier
                                .width(18.dp)
                                .height(18.dp)
                                .clickable {
                                    println("icon click")
                                }, contentDescription = "", tint = COLOR_TEXT
                        )
                    }
                    Text(
                        text = "09:30 PM",
                        fontSize = 14.sp,
                        color = Color.White,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_regular)))
                    )

                    Text(
                        text = "${item.doctorName}",
                        fontSize = 18.sp,
                        color = Color.White,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_bold)))
                    )
                    Text(
                        text = "${item.depcription}",
                        fontSize = 15.sp,
                        color = DESCRIPTION_DAILY,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_sans_regular)))
                    )
                }
            }
        }
    }
}