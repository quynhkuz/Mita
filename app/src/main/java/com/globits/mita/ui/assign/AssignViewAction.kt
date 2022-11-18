package com.globits.mita.ui.assign

import com.globits.mita.core.MitaViewModelAction
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.model.labtestassign.LabTestAssign


sealed class AssignViewAction : MitaViewModelAction {
    object GetUsers : AssignViewAction()
    data class SetPatientDetail(val patient: Patient) : AssignViewAction()
    data class GetPatients(val status :Int) : AssignViewAction()
    object GetLabTestAssignTemplate : AssignViewAction()
    data class GetLabTestAssign(val patientId: Long) : AssignViewAction()
    data class SaveLabTestAssign(val patientId: Long, val listLabTestAssign: List<LabTestAssign>) : AssignViewAction()

}