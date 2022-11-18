package com.globits.mita.ui.patients

import com.airbnb.mvrx.*
import com.globits.mita.core.MitaViewModel
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.repository.TestRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class PatientViewModel @AssistedInject constructor(
    @Assisted state: PatientViewState,
    val repository: TestRepository
) :
    MitaViewModel<PatientViewState, PatientViewAction, PatientViewEvent>(state) {
    init {

    }

    override fun handle(action: PatientViewAction) {
        when (action) {
            is PatientViewAction.GetLabTest -> getLabTest(action.patientId)
            is PatientViewAction.GetLabTestXRay -> getLabTestXRay(action.patientId)
            is PatientViewAction.GetPrescription -> getPrescription(action.patientId)
            else -> {}
        }
    }

    private fun getPrescription(patientId: Int) {
        setState {
            copy(asyncPresentation= Loading())
        }
        repository.getPrescription(patientId).execute {
            copy(asyncPresentation = it)
        }
    }

    private fun getLabTestXRay(patientId: Int) {
        setState {
            copy(asyncLabTestXRay = Loading())
        }
        repository.getLabTestXRay(patientId).execute {
            copy(asyncLabTestXRay = it)
        }
    }

    private fun getLabTest(patientId: Int) {
        setState {
            copy(asyncLabTest= Loading())
        }
        repository.getLabTest(patientId).execute {
            copy(asyncLabTest = it)
        }
    }



    @AssistedFactory
    interface Factory {
        fun create(initialState: PatientViewState): PatientViewModel
    }

    companion object : MvRxViewModelFactory<PatientViewModel, PatientViewState> {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: PatientViewState
        ): PatientViewModel {
            val factory = when (viewModelContext) {
                is FragmentViewModelContext -> viewModelContext.fragment as? Factory
                is ActivityViewModelContext -> viewModelContext.activity as? Factory
            }

            return factory?.create(state)
                ?: error("You should let your activity/fragment implements Factory interface")
        }
    }
}