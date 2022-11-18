package com.globits.mita.ui.assign

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.globits.mita.data.model.Page
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.labtestassign.LabTestAssign
import com.globits.mita.data.model.labtestassign.LabTestAssignTemplate

data class AssignViewState(
    val asyncUsers: Async<List<Patient>> = Uninitialized,
    val asyncPatients: Async<Page<Patient>> = Uninitialized,
    val asyncLabTestAssign: Async<List<LabTestAssign>> = Uninitialized,
    val asyncSaveLabTestAssign: Async<List<LabTestAssign>> = Uninitialized,
    val asyncLabTestAssignTemplate: Async<List<LabTestAssignTemplate>> = Uninitialized,
    var patient: Patient? = null
) : MvRxState {
    fun isLoading() = asyncUsers is Loading ||
            asyncPatients is Loading ||
            asyncLabTestAssign is Loading ||
            asyncSaveLabTestAssign is Loading ||
            asyncLabTestAssignTemplate is Loading
}