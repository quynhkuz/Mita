package com.globits.mita.ui.treatment

import com.globits.mita.core.MitaViewModelAction
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.ui.assign.AssignViewAction
import com.globits.mita.ui.pacs.PacsViewAction


sealed class TreatmentViewAction : MitaViewModelAction {

    object GetUsers : TreatmentViewAction()
    data class SetPatientDetail(var patient: Patient): TreatmentViewAction()
    data class GetPatients(var status :Int) : TreatmentViewAction()
}