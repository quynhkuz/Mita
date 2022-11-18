package com.globits.mita.ui.security

import com.globits.mita.core.MitaViewModelAction
import com.globits.mita.data.model.TokenResponse


sealed class SecurityViewAction : MitaViewModelAction {
    data class LogginAction(var userName: String, var password: String) : SecurityViewAction()
    data class SaveTokenAction(var token: TokenResponse) : SecurityViewAction()
    object GetUserCurrent : SecurityViewAction()
}