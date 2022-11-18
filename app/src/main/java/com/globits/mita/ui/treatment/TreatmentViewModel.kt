package com.globits.mita.ui.treatment

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.airbnb.mvrx.*
import com.globits.mita.core.MitaViewModel
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.repository.TestRepository
import com.globits.mita.ui.pacs.PacsViewAction
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class TreatmentViewModel @AssistedInject constructor(
    @Assisted state: TreatmentViewState,
    val repository: TestRepository
) :
    MitaViewModel<TreatmentViewState, TreatmentViewAction, TreatmentViewEvent>(state) {


    private val _getPatient = MutableStateFlow<PagingData<Patient>>(PagingData.empty())
    val getPatient = _getPatient


    init {
        getPatient(1)
    }

    override fun handle(action: TreatmentViewAction) {
        when (action) {
            is TreatmentViewAction.GetPatients -> getPatient(action.status)
            is TreatmentViewAction.SetPatientDetail -> setPatientDetail(action.patient)
            else -> {}
        }
    }

    private fun setPatientDetail(patient: Patient) {
        setState { copy(patient=patient)}
    }


    fun getPatient(status :Int) {
        viewModelScope.launch {
            repository.getPatient(status).cachedIn(viewModelScope).collect {
                _getPatient.value = it
            }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(initialState: TreatmentViewState): TreatmentViewModel
    }

    companion object : MvRxViewModelFactory<TreatmentViewModel, TreatmentViewState> {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: TreatmentViewState
        ): TreatmentViewModel {
            val factory = when (viewModelContext) {
                is FragmentViewModelContext -> viewModelContext.fragment as? Factory
                is ActivityViewModelContext -> viewModelContext.activity as? Factory
            }

            return factory?.create(state)
                ?: error("You should let your activity/fragment implements Factory interface")
        }
    }
}