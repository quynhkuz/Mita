package com.globits.mita.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.globits.mita.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_sans_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
val styleText = TextStyle(
    fontFamily = FontFamily(Font(R.font.nunito_sans_regular)),
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    color = TEXT_INFO_PATIENT
)
val styleText1 = TextStyle(
    fontFamily = FontFamily(Font(R.font.nunito_sans_semi_bold)),
    fontWeight = FontWeight.Bold,
    fontSize = 13.sp,
    color = PRIMARY_COLOR
)
val styleText2 = TextStyle(
    fontFamily = FontFamily(Font(R.font.nunito_sans_regular)),
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    color = TEXT_COLOR4
)

val styleText3 = TextStyle(
    fontFamily = FontFamily(Font(R.font.nunito_sans_extra_bold)),
    fontWeight = FontWeight.ExtraBold,
    fontSize = 14.sp,
    color = PRIMARY_COLOR
)

val styleText4 = TextStyle(
    fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
    fontWeight = FontWeight.Bold,
    fontSize = 13.sp,
    color = PRIMARY_COLOR
)

val styleText5 = TextStyle(
    fontFamily = FontFamily(Font(R.font.nunito_sans_regular)),
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    color = TEXT_RED
)