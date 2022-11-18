package com.globits.mita.ui.home

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.globits.mita.data.model.User

data class HomeViewState(
    val userCurrent:Async<User> = Uninitialized,
    ) : MvRxState {
    fun isLoading() =
            userCurrent is Loading
}