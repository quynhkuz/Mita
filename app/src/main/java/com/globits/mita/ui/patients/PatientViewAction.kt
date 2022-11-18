package com.globits.mita.ui.patients

import com.globits.mita.core.MitaViewModelAction
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter


sealed class PatientViewAction : MitaViewModelAction {

    data class GetLabTest(var patientId :Int) : PatientViewAction()
    data class GetLabTestXRay(var patientId :Int) : PatientViewAction()
    data class GetPrescription(var patientId :Int) : PatientViewAction()
}