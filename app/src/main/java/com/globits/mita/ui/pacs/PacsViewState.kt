package com.globits.mita.ui.pacs

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.globits.mita.data.model.Document
import com.globits.mita.data.model.Page
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.Responsive

data class PacsViewState(
    var asyncUsers: Async<Responsive<List<Patient>>> = Uninitialized,
    var asyncPatients: Async<Page<Patient>> = Uninitialized,
    var patient: Patient? = null,
    var document: Document? = null
) : MvRxState {
    fun isLoading() = asyncUsers is Loading
}