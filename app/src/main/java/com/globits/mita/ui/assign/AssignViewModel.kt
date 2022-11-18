package com.globits.mita.ui.assign

import android.util.Log
import androidx.compose.runtime.saveable.listSaver
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.airbnb.mvrx.*
import com.globits.mita.core.MitaViewModel
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.model.labtestassign.LabTestAssign
import com.globits.mita.data.repository.LabTestRepository
import com.globits.mita.data.repository.TestRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class AssignViewModel @AssistedInject constructor(
    @Assisted state: AssignViewState,
    private val repository: TestRepository,
    private val labTestRepository: LabTestRepository
) :
    MitaViewModel<AssignViewState, AssignViewAction, AssignViewEvent>(state) {

    private val _getPatient = MutableStateFlow<PagingData<Patient>>(PagingData.empty())
    val getPatient = _getPatient

    init {
        getPatients(1)

    }

    override fun handle(action: AssignViewAction) {
        when (action) {
            is AssignViewAction.GetPatients -> getPatients(action.status)
            is AssignViewAction.SetPatientDetail -> setPatientDetail(action.patient)
            is AssignViewAction.GetLabTestAssign -> getLabTestAssign(action.patientId)
            is AssignViewAction.GetLabTestAssignTemplate -> getLabTestTemplate()
            is AssignViewAction.SaveLabTestAssign -> saveLabTestAssign(action.patientId,action.listLabTestAssign)
            else -> {}
        }
    }

    private fun saveLabTestAssign(patientId :Long,listLabTestAssign: List<LabTestAssign>) {
        setState { copy(asyncSaveLabTestAssign = Loading()) }
        labTestRepository.saveLabTestAssign(patientId,listLabTestAssign).execute {
            _viewEvents.post(AssignViewEvent.SaveLabTestAssignEvent)
            copy(asyncSaveLabTestAssign = it)

        }
    }

    private fun getLabTestAssign(patientId: Long) {
        setState { copy(asyncLabTestAssign = Loading()) }
        labTestRepository.getLabTestAssign(patientId).execute {

            _viewEvents.post(AssignViewEvent.GetLabTestAssignEvent)
            copy(asyncLabTestAssign = it)

        }
    }


    private fun getLabTestTemplate() {
        setState { copy(asyncLabTestAssignTemplate = Loading()) }
        labTestRepository.getLabTestAssignTemplate().execute {
            copy(asyncLabTestAssignTemplate = it)
        }
    }

    private fun setPatientDetail(patient: Patient) {
        setState { copy(patient = patient) }
    }

    private fun getPatients(status :Int) {
        viewModelScope.launch {
            repository.getPatient(status).cachedIn(viewModelScope).collect {
                _getPatient.value = it
            }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(initialState: AssignViewState): AssignViewModel
    }

    companion object : MvRxViewModelFactory<AssignViewModel, AssignViewState> {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: AssignViewState
        ): AssignViewModel {
            val factory = when (viewModelContext) {
                is FragmentViewModelContext -> viewModelContext.fragment as? Factory
                is ActivityViewModelContext -> viewModelContext.activity as? Factory
            }

            return factory?.create(state)
                ?: error("You should let your activity/fragment implements Factory interface")
        }
    }
}