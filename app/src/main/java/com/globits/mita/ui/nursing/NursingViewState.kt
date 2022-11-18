package com.globits.mita.ui.nursing

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.globits.mita.data.model.Page
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.Responsive

data class NursingViewState(
    var asyncUsers: Async<Responsive<List<Patient>>> = Uninitialized,
    var asyncPatients: Async<Page<Patient>> = Uninitialized,
    var patient: Patient? = null
) : MvRxState {
    fun isLoading() = asyncUsers is Loading
}