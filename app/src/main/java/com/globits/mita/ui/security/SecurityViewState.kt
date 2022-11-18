package com.globits.mita.ui.security

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.globits.mita.data.model.TokenResponse
import com.globits.mita.data.model.User

data class SecurityViewState(
    var asyncLogin: Async<TokenResponse> = Uninitialized,
    var asyncUserCurrent: Async<User> = Uninitialized
) : MvRxState {
    fun isLoading() = asyncLogin is Loading || asyncUserCurrent is Loading
}