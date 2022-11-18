package com.globits.mita.ui.security

import com.globits.mita.core.MitaViewEvents

sealed class SecurityViewEvent:MitaViewEvents {
    object ReturnSignInEvent: SecurityViewEvent()
    object ReturnResetPassEvent: SecurityViewEvent()
    object GetUserEvent:SecurityViewEvent()
}