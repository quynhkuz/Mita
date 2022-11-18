package com.globits.mita.ui.patients

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.globits.mita.data.model.labtest.LabTest
import com.globits.mita.data.model.labtestxray.LabTestXRay
import com.globits.mita.data.model.prescriptions.PresCripTion

data class PatientViewState(
    var asyncPresentation: Async<List<PresCripTion>> = Uninitialized,
    var asyncLabTestXRay: Async<List<LabTestXRay>> = Uninitialized,
    var asyncLabTest: Async<List<LabTest>> = Uninitialized,

) : MvRxState {
    fun isLoading() = asyncPresentation is Loading
}