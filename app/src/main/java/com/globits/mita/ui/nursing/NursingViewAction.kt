package com.globits.mita.ui.nursing

import com.globits.mita.core.MitaViewModelAction
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.ui.assign.AssignViewAction
import com.globits.mita.ui.pacs.PacsViewAction
import com.globits.mita.ui.treatment.TreatmentViewAction


sealed class NursingViewAction : MitaViewModelAction {

    object GetUsers : NursingViewAction()
    data class SetPatientDetail(var patient: Patient): NursingViewAction()
    data class GetPatients(var status :Int) : NursingViewAction()
}