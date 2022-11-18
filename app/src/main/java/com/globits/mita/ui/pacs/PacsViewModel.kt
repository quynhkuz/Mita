package com.globits.mita.ui.pacs

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.airbnb.mvrx.*
import com.globits.mita.core.MitaViewModel
import com.globits.mita.data.model.Document
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.repository.TestRepository
import com.globits.mita.ui.assign.AssignViewAction
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class PacsViewModel @AssistedInject constructor(
    @Assisted state: PacsViewState,
    val repository: TestRepository
) :
    MitaViewModel<PacsViewState, PacsViewAction, PacsViewEvent>(state) {

    private val _getPatient = MutableStateFlow<PagingData<Patient>>(PagingData.empty())
    val getPatient = _getPatient

    init {
        getPatients(1)
    }

    override fun handle(action: PacsViewAction) {
        when (action) {
            is PacsViewAction.GetPatients -> getPatients(action.status)
            is PacsViewAction.SetPatientDetail -> setPatientDetail(action.patient)
            is PacsViewAction.SetDocument -> setDocument(action.document)
            else -> {}
        }
    }

    private fun setDocument(document: Document) {
        setState { copy(document  =document)}
    }



    private fun setPatientDetail(patient: Patient) {
        setState { copy(patient=patient)}
    }

    private fun getPatients(status :Int) {
        viewModelScope.launch {
            Log.d("AAA","viewModelScope")
            repository.getPatient(status).cachedIn(viewModelScope).collect {
                _getPatient.value = it
            }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(initialState: PacsViewState): PacsViewModel
    }

    companion object : MvRxViewModelFactory<PacsViewModel, PacsViewState> {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: PacsViewState
        ): PacsViewModel {
            val factory = when (viewModelContext) {
                is FragmentViewModelContext -> viewModelContext.fragment as? Factory
                is ActivityViewModelContext -> viewModelContext.activity as? Factory
            }

            return factory?.create(state)
                ?: error("You should let your activity/fragment implements Factory interface")
        }
    }
}