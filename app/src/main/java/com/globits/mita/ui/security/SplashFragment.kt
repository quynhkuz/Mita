package com.globits.mita.ui.security

import android.app.PendingIntent.getActivity
import android.os.Build
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.ui.theme.PRIMARY_COLOR
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import javax.inject.Inject

class SplashFragment @Inject constructor() : MitaBaseFragment() {
    @Composable
    override fun SetLayout() {

        //setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(getActivity()?.getColor(R.color.primary_color) ?: R.color.white)
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        };

       MaterialTheme {
           SetSplashScreen()
       }
    }

}

@Preview
@Composable
fun DefaulPreview() {
    SetSplashScreen()
}
@Composable
fun SetSplashScreen(){


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PRIMARY_COLOR),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(128.sdp),
            painter = painterResource(id = R.drawable.img_icon_mita_white),
            contentDescription = ""
        )
        Text(
            text = "Mita",
            color = Color.White,
            fontSize = 50.ssp,
            fontFamily = FontFamily(Font(R.font.nunito_sans_semi_bold))
        )
    }
}