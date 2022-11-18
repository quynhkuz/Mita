package com.globits.mita.ui.security

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.data.model.TokenResponse
import com.globits.mita.data.network.SessionManager
import com.globits.mita.ui.MainActivity
import com.globits.mita.ui.theme.BACKGROUND_EDIT_TEXT_LOGIN
import com.globits.mita.ui.theme.COLOR_TEXT
import com.globits.mita.ui.theme.PRIMARY_COLOR
import com.globits.mita.ui.theme.STROKE_EDIT_TEXT_LOGIN
import com.globits.mita.utils.snackbar
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import javax.inject.Inject

class LoginFragment @Inject constructor() : MitaBaseFragment() {

    val viewModel: SecurityViewModel by activityViewModel()

    @Composable
    override fun SetLayout() {

        MaterialTheme() {
            LoginScreen(onClickLogin = { username, password ->
                loginSubmit(username, password)
            })
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(getActivity()?.getColor(R.color.white) ?: R.color.white)
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        };

    }

    private fun loginSubmit(username: String, password: String) {
        if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            viewModel.handle(SecurityViewAction.LogginAction(username, password))
        } else {
            activity?.snackbar("Bạn chưa nhập đủ username và password")
        }
    }

    override fun invalidate(): Unit = withState(viewModel) {
        when (it.asyncLogin) {
            is Success -> {
                it.asyncLogin.invoke()?.let { token ->
                    val sessionManager =
                        context?.let { it1 -> SessionManager(it1.applicationContext) }
                    token.accessToken?.let { it1 -> sessionManager!!.saveAuthToken(it1) }
                    token.refreshToken?.let { it1 -> sessionManager!!.saveAuthTokenRefresh(it1) }
                }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.login_success),
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                activity?.finish()
            }
            is Fail -> {
                if ((it.asyncLogin as Fail<TokenResponse>).error.message?.contains("401") == true) {
                    activity?.snackbar("Tài khoản mật khẩu không chính xác.")
                }
                activity?.snackbar("Đã có lỗi xảy ra")
            }
            else -> {}
        }

    }

}

@Composable
fun LoginScreen(onClickLogin: (String, String) -> Unit) {
    var txtUserName by remember {
        mutableStateOf("")
    }
    var txtPassword by remember {
        mutableStateOf("")
    }
    var isShowPassword by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.sdp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(64.sdp),
                painter = painterResource(id = R.drawable.icon_launcher),
                contentDescription = ""
            )
            Text(
                text = "Mita",
                color = PRIMARY_COLOR,
                fontSize = 25.ssp,
                fontFamily = FontFamily(Font(R.font.nunito_sans_bold))
            )
        }


        Text(
            modifier = Modifier.padding(top = 44.sdp),
            text = "Đăng nhập",
            color = PRIMARY_COLOR,
            fontSize = 32.ssp,
            fontFamily = FontFamily(Font(R.font.nunito_sans_bold))
        )

        val iconStart: @Composable (() -> Unit) = {
            Icon(
                painter = painterResource(id = R.drawable.img_icon_user),
                modifier = Modifier
                    .width(18.sdp)
                    .height(18.sdp), contentDescription = "", tint = COLOR_TEXT
            )
        }
        val iconEnd: @Composable (() -> Unit) =
            {
                Icon(
                    painter = painterResource(id = if (isShowPassword) R.drawable.show_eye_icon else R.drawable.hide_icon_password),
                    modifier = Modifier
                        .width(18.sdp)
                        .height(18.sdp)
                        .clickable {
                            isShowPassword = !isShowPassword

                        }, contentDescription = "", tint = COLOR_TEXT
                )
            }
        TextField(
            value = txtUserName,
            placeholder = { Text(text = "Tên đăng nhập", color = COLOR_TEXT) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.sdp)
                .border(
                    width = 1.sdp,
                    color = STROKE_EDIT_TEXT_LOGIN,
                    shape = RoundedCornerShape(10.sdp)
                )
                .clip(shape = RoundedCornerShape(10.sdp)),
            onValueChange = { txtUserName = it },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = BACKGROUND_EDIT_TEXT_LOGIN
            ),

            leadingIcon = iconStart,

            )
        TextField(
            value = txtPassword,
            placeholder = { Text(text = "Mật khẩu", color = COLOR_TEXT) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp)
                .border(
                    width = 1.sdp,
                    color = STROKE_EDIT_TEXT_LOGIN,
                    shape = RoundedCornerShape(10.sdp),
                )
                .clip(shape = RoundedCornerShape(10.sdp)),
            onValueChange = { txtPassword = it },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = BACKGROUND_EDIT_TEXT_LOGIN
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.img_icon_password),
                    modifier = Modifier
                        .width(18.sdp)
                        .height(18.sdp), contentDescription = "", tint = COLOR_TEXT
                )
            },
            trailingIcon = iconEnd,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation()
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.sdp),
            text = "Quên mật khẩu",
            color = PRIMARY_COLOR,
            fontSize = 14.ssp, textAlign = TextAlign.End,
            fontFamily = FontFamily(Font(R.font.nunito_sans_regular))
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.sdp),
            onClick = {
                onClickLogin(txtUserName, txtPassword)
            },
            colors = ButtonDefaults.buttonColors(PRIMARY_COLOR),
            shape = RoundedCornerShape(10.sdp)
        ) {
            Text(
                text = "Đăng nhập",
                color = Color.White,
                modifier = Modifier.padding(top = 8.sdp, bottom = 8.sdp)
            )
        }
    }


}

@Preview
@Composable
fun DefaulPreviewLoginScreen() {
    LoginScreen() { _, _ -> }
}