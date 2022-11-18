package com.globits.mita.ui.nursing

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.airbnb.mvrx.*
import com.globits.mita.core.MitaViewModel
import com.globits.mita.data.model.Patient
import com.globits.mita.data.repository.TestRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class NursingViewModel @AssistedInject constructor(
    @Assisted state: NursingViewState,
    val repository: TestRepository
) :
    MitaViewModel<NursingViewState, NursingViewAction, NursingViewEvent>(state) {


    private val _getPatient = MutableStateFlow<PagingData<Patient>>(PagingData.empty())
    val getPatient = _getPatient


    init {
        getPatient(1)
    }

    override fun handle(action: NursingViewAction) {
        when (action) {
            is NursingViewAction.GetPatients -> getPatient(action.status)
            is NursingViewAction.SetPatientDetail -> setPatientDetail(action.patient)
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
        fun create(initialState: NursingViewState): NursingViewModel
    }

    companion object : MvRxViewModelFactory<NursingViewModel, NursingViewState> {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: NursingViewState
        ): NursingViewModel {
            val factory = when (viewModelContext) {
                is FragmentViewModelContext -> viewModelContext.fragment as? Factory
                is ActivityViewModelContext -> viewModelContext.activity as? Factory
            }

            return factory?.create(state)
                ?: error("You should let your activity/fragment implements Factory interface")
        }
    }
}